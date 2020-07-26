package com.arkapp.saa.ui.newCourse;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arkapp.saa.R;
import com.arkapp.saa.data.firestore.Queries;
import com.arkapp.saa.data.models.Course;
import com.arkapp.saa.data.preferences.PrefRepository;
import com.arkapp.saa.databinding.FragmentNewCourseBinding;
import com.google.firebase.Timestamp;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.arkapp.saa.utility.ViewUtilsKt.enableTouch;
import static com.arkapp.saa.utility.ViewUtilsKt.getFormattedDate;
import static com.arkapp.saa.utility.ViewUtilsKt.getValue;
import static com.arkapp.saa.utility.ViewUtilsKt.hide;
import static com.arkapp.saa.utility.ViewUtilsKt.isDoubleClicked;
import static com.arkapp.saa.utility.ViewUtilsKt.isEmpty;
import static com.arkapp.saa.utility.ViewUtilsKt.show;
import static com.arkapp.saa.utility.ViewUtilsKt.showAlertDialog;
import static com.arkapp.saa.utility.ViewUtilsKt.toast;

public class NewCourseFragment extends Fragment {

    private FragmentNewCourseBinding binding;

    private Course course = new Course();
    private Queries queries = new Queries();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewCourseBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PrefRepository prefSession = new PrefRepository(requireContext());

        initSpinner();
        initCalendar();

        if (prefSession.getCurrentCourse() != null) {
            show(binding.btDelete);
            binding.btSubmit.setText(getString(R.string.update));

            course = prefSession.getCurrentCourse();
            binding.courseTitleEt.setText(course.getTitle());
            binding.courseOutlineEt.setText(course.getOutline());
            binding.courseFeeEt.setText(course.getFees());
            binding.courseDurationEt.setText(course.getDuration());
            binding.startDateEt.setText(getFormattedDate(course.getStartDate().toDate()));
            binding.endDateEt.setText(getFormattedDate(course.getEndDate().toDate()));

            List<String> jobSector = Arrays.asList(getResources().getStringArray(R.array.job_sector));
            List<String> school = Arrays.asList(getResources().getStringArray(R.array.school));
            List<String> courseCategory = Arrays.asList(getResources().getStringArray(R.array.course_category));

            binding.spinnerJobSector.setSelection(jobSector.indexOf(course.getJobSector()));
            binding.spinnerSchool.setSelection(school.indexOf(course.getSchool()));
            binding.spinnerCourseCategory.setSelection(courseCategory.indexOf(course.getCategory()));

        }

        binding.btSubmit.setOnClickListener(v -> {
            show(binding.progress);
            if (isDoubleClicked(1000)) return;

            if (isEmpty(binding.courseTitleEt)) {
                binding.inputCourseTitle.setError("Title required!");

                hide(binding.progress);
                enableTouch(requireActivity().getWindow());
                return;
            }

            if (isEmpty(binding.courseOutlineEt)) {
                binding.inputCourseOutline.setError("Title outline required!");

                hide(binding.progress);
                enableTouch(requireActivity().getWindow());
                return;
            }

            if (isEmpty(binding.courseFeeEt)) {
                binding.inputCourseFee.setError("Fee required!");

                hide(binding.progress);
                enableTouch(requireActivity().getWindow());
                return;
            }

            if (isEmpty(binding.courseDurationEt)) {
                binding.inputCourseDuration.setError("Duration required!");

                hide(binding.progress);
                enableTouch(requireActivity().getWindow());
                return;
            }

            if (isEmpty(binding.startDateEt)) {
                binding.inputStartDate.setError("Start Date required!");

                hide(binding.progress);
                enableTouch(requireActivity().getWindow());
                return;
            }

            if (isEmpty(binding.endDateEt)) {
                binding.inputEndDate.setError("End Date required!");

                hide(binding.progress);
                enableTouch(requireActivity().getWindow());
                return;
            }

            if (binding.btSubmit.getText().toString().equals(getString(R.string.update)))
                updateCourse();
            else
                addCourseToDB();
        });

        binding.btDelete.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;

            showAlertDialog(requireContext(),
                            "Delete Course!",
                            "Are you sure?",
                            "Delete",
                            "No",
                            (DialogInterface.OnClickListener) (dialog, which) -> {
                                show(binding.progress);

                                queries.deleteCourse(course).addOnCompleteListener(task -> {
                                    hide(binding.progress);
                                    enableTouch(requireActivity().getWindow());

                                    if (task.isSuccessful()) {
                                        toast(requireContext(), "Successfully Deleted Course!");
                                        requireActivity().onBackPressed();
                                    } else
                                        toast(requireContext(), "Oops Something went wrong. Try again later!");
                                });
                            });
        });
    }

    private void updateCourse() {
        setAllValues();

        queries.updateCourse(course).addOnCompleteListener(task -> {
            hide(binding.progress);
            enableTouch(requireActivity().getWindow());

            if (task.isSuccessful()) {
                toast(requireContext(), "Successfully Updated Course!");
                requireActivity().onBackPressed();
            } else toast(requireContext(), "Oops Something went wrong. Try again later!");
        });
    }

    private void addCourseToDB() {
        setAllValues();

        queries.addCourse(course).addOnCompleteListener(task -> {
            hide(binding.progress);
            enableTouch(requireActivity().getWindow());

            if (task.isSuccessful()) {
                toast(requireContext(), "Successfully Added Course!");
                requireActivity().onBackPressed();
            } else toast(requireContext(), "Oops Something went wrong. Try again later!");
        });
    }

    private void setAllValues() {
        course.setTitle(getValue(binding.courseTitleEt));
        course.setOutline(getValue(binding.courseOutlineEt));
        course.setFees(getValue(binding.courseFeeEt));
        course.setDuration(getValue(binding.courseDurationEt));
    }

    private void initCalendar() {
        binding.startDateEt.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;

            Calendar currentDate = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener listener = (view1, year, month, dayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                course.setStartDate(new Timestamp(selectedDate.getTime()));
                binding.startDateEt.setText(getFormattedDate(selectedDate.getTime()));
            };

            DatePickerDialog datePicker = new DatePickerDialog(requireContext(),
                                                               listener,
                                                               currentDate.get(Calendar.YEAR),
                                                               currentDate.get(Calendar.MONTH),
                                                               currentDate.get(Calendar.DAY_OF_MONTH));

            datePicker.getDatePicker().setMinDate(currentDate.getTimeInMillis());
            datePicker.show();

        });

        binding.endDateEt.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;

            Calendar currentDate = Calendar.getInstance();
            if (course.getStartDate() != null) {
                currentDate.setTime(course.getStartDate().toDate());
            }

            DatePickerDialog.OnDateSetListener listener = (view1, year, month, dayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                course.setEndDate(new Timestamp(selectedDate.getTime()));
                binding.endDateEt.setText(getFormattedDate(selectedDate.getTime()));
            };

            DatePickerDialog datePicker = new DatePickerDialog(requireContext(),
                                                               listener,
                                                               currentDate.get(Calendar.YEAR),
                                                               currentDate.get(Calendar.MONTH),
                                                               currentDate.get(Calendar.DAY_OF_MONTH));

            datePicker.getDatePicker().setMinDate(currentDate.getTimeInMillis());
            datePicker.show();
        });
    }

    private void initSpinner() {
        binding.spinnerCourseCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course.setCategory(binding.spinnerCourseCategory.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        binding.spinnerJobSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course.setJobSector(binding.spinnerJobSector.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        binding.spinnerSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course.setSchool(binding.spinnerSchool.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}