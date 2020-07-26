package com.arkapp.saa.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arkapp.saa.R;
import com.arkapp.saa.data.preferences.PrefRepository;
import com.arkapp.saa.utility.ViewUtilsKt;

import static androidx.navigation.fragment.NavHostFragment.findNavController;
import static com.arkapp.saa.utility.ViewUtilsKt.isDoubleClicked;

public class SplashFragment extends Fragment {

    private PrefRepository prefRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefRepository = new PrefRepository(requireContext());
        initSignUpBtn();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.loadSplash();
    }

    private void initSignUpBtn() {
        if (!prefRepository.setLoggedIn()) {

            getView().findViewById(R.id.signUpBtn).setOnClickListener(it -> {
                if (!isDoubleClicked(1000L)) {
                    findNavController(this).navigate(R.id.action_splashFragment_to_signUpFragment);
                }
            });
        } else
            ViewUtilsKt.hide(getView().findViewById(R.id.signUpBtn));

    }

    private void loadSplash() {
        Runnable runnable = () -> {
            if (prefRepository.setLoggedIn())
                findNavController(this).navigate(R.id.action_splashFragment_to_homeFragment);
        };

        new Handler().postDelayed(runnable, 1500);
    }
}