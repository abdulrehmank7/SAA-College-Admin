package com.arkapp.saa.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arkapp.saa.R;
import com.arkapp.saa.data.preferences.PrefRepository;
import com.arkapp.saa.databinding.FragmentHomeBinding;

import static androidx.navigation.fragment.NavHostFragment.findNavController;
import static com.arkapp.saa.utility.ViewUtilsKt.isDoubleClicked;
import static com.arkapp.saa.utility.ViewUtilsKt.showAlertDialog;
import static com.arkapp.saa.utility.ViewUtilsKt.toastShort;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private PrefRepository prefSession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefSession = new PrefRepository(requireContext());
        prefSession.setCurrentCourse(null);

        binding.cvNewCourse.setOnClickListener(v -> {
            if (isDoubleClicked(1)) return;

            findNavController(this).navigate(R.id.action_homeFragment_to_newCourseFragment);
        });

        binding.cvCourse.setOnClickListener(v -> {
            if (isDoubleClicked(1)) return;

            findNavController(this).navigate(R.id.action_homeFragment_to_coursesFragment);
        });

        binding.cvLogout.setOnClickListener(v -> {
            showAlertDialog(requireContext(), "Logout", "Do you want to logout?", "Logout", "Cancel", (DialogInterface.OnClickListener) (new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialog, int $noName_1) {
                    prefSession.clearData();
                    findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_splashFragment);
                    toastShort(requireContext(), "Logged Out!");
                    dialog.dismiss();
                }
            }));
        });
    }
}