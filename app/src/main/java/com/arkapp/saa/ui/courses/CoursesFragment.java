package com.arkapp.saa.ui.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arkapp.saa.data.firestore.Queries;
import com.arkapp.saa.data.models.Course;
import com.arkapp.saa.data.preferences.PrefRepository;
import com.arkapp.saa.databinding.FragmentCoursesBinding;

import static androidx.navigation.fragment.NavHostFragment.findNavController;
import static com.arkapp.saa.data.firestore.Utils.isValidTask;
import static com.arkapp.saa.utility.ViewUtilsKt.disableTouch;
import static com.arkapp.saa.utility.ViewUtilsKt.enableTouch;
import static com.arkapp.saa.utility.ViewUtilsKt.hide;
import static com.arkapp.saa.utility.ViewUtilsKt.initVerticalAdapter;
import static com.arkapp.saa.utility.ViewUtilsKt.show;
import static com.arkapp.saa.utility.ViewUtilsKt.toast;

public class CoursesFragment extends Fragment {


    private FragmentCoursesBinding binding;
    private Queries queries = new Queries();
    private PrefRepository prefSession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCoursesBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        show(binding.progress);
        disableTouch(requireActivity().getWindow());

        prefSession = new PrefRepository(requireContext());

        queries.getCourses()
                .addOnCompleteListener(task -> {
                    hide(binding.progress);
                    enableTouch(requireActivity().getWindow());

                    if (isValidTask(task)) {
                        CourseListAdapter adapter = new CourseListAdapter(prefSession,task.getResult().toObjects(Course.class), findNavController(this));
                        initVerticalAdapter(binding.rvCourses, adapter, true);
                    } else toast(requireContext(), "No Course found!");
                });
    }
}