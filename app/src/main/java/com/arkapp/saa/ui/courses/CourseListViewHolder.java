package com.arkapp.saa.ui.courses;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.arkapp.saa.databinding.RvCourseBinding;

import org.jetbrains.annotations.NotNull;


public final class CourseListViewHolder extends ViewHolder {
    @NotNull
    private final RvCourseBinding viewBinding;

    public CourseListViewHolder(@NotNull RvCourseBinding viewBinding) {
        super(viewBinding.getRoot());
        this.viewBinding = viewBinding;
    }

    @NotNull
    public final RvCourseBinding getViewBinding() {
        return this.viewBinding;
    }
}
