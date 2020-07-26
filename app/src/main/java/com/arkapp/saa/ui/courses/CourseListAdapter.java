package com.arkapp.saa.ui.courses;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.arkapp.saa.R;
import com.arkapp.saa.data.models.Course;
import com.arkapp.saa.data.preferences.PrefRepository;
import com.arkapp.saa.databinding.RvCourseBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.arkapp.saa.utility.ViewUtilsKt.getFormattedDate;
import static com.arkapp.saa.utility.ViewUtilsKt.isDoubleClicked;

public final class CourseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Course> courses;
    private final NavController navController;
    private final PrefRepository prefSession;

    public CourseListAdapter(PrefRepository prefSession,
                             @NotNull List<Course> courses,
                             @NotNull NavController navController) {
        this.courses = courses;
        this.navController = navController;
        this.prefSession = prefSession;
    }

    @NotNull
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return (ViewHolder) (new CourseListViewHolder((RvCourseBinding)
                                                              DataBindingUtil
                                                                      .inflate(
                                                                              LayoutInflater.from(parent.getContext()),
                                                                              R.layout.rv_course,
                                                                              parent,
                                                                              false)));
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        RvCourseBinding binding = ((CourseListViewHolder) holder).getViewBinding();
        final Course course = (Course) this.courses.get(position);

        binding.name.setText(course.getTitle());
        binding.tvStartDate.setText(getFormattedDate(course.getStartDate().toDate()));
        binding.tvEndDate.setText(getFormattedDate(course.getEndDate().toDate()));
        binding.parent.setOnClickListener(v -> {
            if (isDoubleClicked(1)) return;
            prefSession.setCurrentCourse(course);
            navController.navigate(R.id.action_coursesFragment_to_newCourseFragment);
        });
    }

    public int getItemCount() {
        return this.courses.size();
    }

    public long getItemId(int position) {
        return (long) (courses.get(position)).hashCode();
    }

}
