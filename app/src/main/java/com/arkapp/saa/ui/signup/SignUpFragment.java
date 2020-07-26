package com.arkapp.saa.ui.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arkapp.saa.R;
import com.arkapp.saa.data.firestore.Queries;
import com.arkapp.saa.data.models.UserData;
import com.arkapp.saa.data.preferences.PrefRepository;
import com.arkapp.saa.databinding.FragmentSignUpBinding;

import static androidx.navigation.fragment.NavHostFragment.findNavController;
import static com.arkapp.saa.data.firestore.Utils.isValidTask;
import static com.arkapp.saa.utility.ViewUtilsKt.disableTouch;
import static com.arkapp.saa.utility.ViewUtilsKt.enableTouch;
import static com.arkapp.saa.utility.ViewUtilsKt.getValue;
import static com.arkapp.saa.utility.ViewUtilsKt.hide;
import static com.arkapp.saa.utility.ViewUtilsKt.isDoubleClicked;
import static com.arkapp.saa.utility.ViewUtilsKt.isEmailValid;
import static com.arkapp.saa.utility.ViewUtilsKt.isEmpty;
import static com.arkapp.saa.utility.ViewUtilsKt.show;
import static com.arkapp.saa.utility.ViewUtilsKt.toast;


public class SignUpFragment extends Fragment {

    private PrefRepository prefRepository;
    private FragmentSignUpBinding binding;
    private Queries queries = new Queries();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefRepository = new PrefRepository(requireContext());

        binding.signUpBtn.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;
            if (binding.signUpBtn.getText().toString().equals(getString(R.string.sign_up))) {
                binding.signUpBtn.setText(getString(R.string.login));
                binding.signUpDesc.setText(getString(R.string.already_have_account));

                hide(binding.loginCard);
                show(binding.signUpCard);

                binding.userNameEt.setText("");
                binding.passwordEt.setText("");

                binding.userName.setError(null);
                binding.password.setError(null);

            } else {
                binding.signUpBtn.setText(getString(R.string.sign_up));
                binding.signUpDesc.setText(getString(R.string.not_have_an_account));

                hide(binding.signUpCard);
                show(binding.loginCard);

                binding.signUpUserNameEt.setText("");
                binding.signUpPasswordEt.setText("");
                binding.signUpConfirmPasswordEt.setText("");

                binding.signUpUserName.setError(null);
                binding.signUpPassword.setError(null);
                binding.signUpConfirmPassword.setError(null);
            }
        });

        binding.loginBtn.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;
            show(binding.loginProgress);
            disableTouch(requireActivity().getWindow());
            onLoginClicked();
        });

        binding.insideSignUpBtn.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;
            show(binding.signupProgress);
            disableTouch(requireActivity().getWindow());
            onSignUpClicked();
        });

        removeErrorOnChange();
    }

    private void removeErrorOnChange() {
        binding.userNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { binding.userName.setError(null);}
        });
        binding.signUpUserFirstNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { binding.signUpUserFirstName.setError(null);}
        });
        binding.signUpUserLastNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { binding.signUpUserLastName.setError(null);}
        });
        binding.signUpPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { binding.signUpPhone.setError(null);}
        });
        binding.passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { binding.password.setError(null);}
        });
        binding.signUpUserNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { binding.signUpUserName.setError(null);}
        });
        binding.signUpEmailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { binding.signUpEmail.setError(null);}
        });
        binding.signUpPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { binding.signUpPassword.setError(null);}
        });
        binding.signUpConfirmPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(
                    Editable s) { binding.signUpConfirmPassword.setError(null);}
        });
    }

    private void onLoginClicked() {
        if (isEmpty(binding.userNameEt)) {
            binding.userName.setError("Username required!");

            hide(binding.loginProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (isEmpty(binding.passwordEt)) {
            binding.password.setError("Password required!");

            hide(binding.loginProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (isEmailValid(getValue(binding.userNameEt))) {
            checkEmailForLogin();
        } else {
            checkUsernameForLogin();
        }
    }

    private void checkUsernameForLogin() {
        queries.checkUsername(getValue(binding.userNameEt))
                .addOnCompleteListener(task -> {
                    hide(binding.loginProgress);
                    enableTouch(requireActivity().getWindow());

                    if (isValidTask(task)) {
                        UserData data = task.getResult().toObjects(UserData.class).get(0);
                        if (data.getPassword().equals(getValue(binding.passwordEt))) {
                            toast(requireContext(), "Login Successful!");
                            prefRepository.setLoggedIn(true);
                            findNavController(this).navigate(R.id.action_signUpFragment_to_homeFragment);
                        } else binding.password.setError("Password is wrong. Please check!");
                    } else binding.userName.setError("Username is wrong. Please check!");
                });
    }

    private void checkEmailForLogin() {
        queries.checkEmail(getValue(binding.userNameEt))
                .addOnCompleteListener(task -> {
                    hide(binding.loginProgress);
                    enableTouch(requireActivity().getWindow());

                    if (isValidTask(task)) {
                        UserData data = task.getResult().toObjects(UserData.class).get(0);
                        if (data.getPassword().equals(getValue(binding.passwordEt))) {
                            toast(requireContext(), "Login Successful!");
                            prefRepository.setLoggedIn(true);
                            findNavController(this).navigate(R.id.action_signUpFragment_to_homeFragment);
                        } else binding.password.setError("Password is wrong. Please check!");
                    } else binding.userName.setError("Email is wrong. Please check!");
                });
    }

    private void onSignUpClicked() {
        if (isEmpty(binding.signUpUserFirstNameEt)) {
            binding.signUpUserFirstName.setError("First name required!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (isEmpty(binding.signUpUserLastNameEt)) {
            binding.signUpUserName.setError("Last name required!");

            hide(binding.signUpUserLastName);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (isEmpty(binding.signUpUserNameEt)) {
            binding.signUpUserName.setError("Username required!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (isEmpty(binding.signUpPhoneEt)) {
            binding.signUpUserName.setError("Phone required!");

            hide(binding.signUpPhone);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (isEmpty(binding.signUpEmailEt)) {
            binding.signUpEmail.setError("Email required!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (binding.signUpUserNameEt.getText().length() < 3) {
            binding.signUpUserName.setError("Invalid Username!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (!isEmailValid(getValue(binding.signUpEmailEt))) {
            binding.signUpEmail.setError("Invalid Email!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (isEmpty(binding.signUpPasswordEt)) {
            binding.signUpPassword.setError("Password required!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (binding.signUpPasswordEt.getText().length() < 3) {
            binding.signUpPassword.setError("Invalid Password! Length should be more than 4");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (isEmpty(binding.signUpConfirmPasswordEt)) {
            binding.signUpConfirmPassword.setError("Password required!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (!getValue(binding.signUpConfirmPasswordEt).equals(getValue(binding.signUpPasswordEt))) {
            binding.signUpConfirmPassword.setError("Password incorrect!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        checkUsernameExists();
    }

    private void checkUsernameExists() {
        queries.checkUsername(getValue(binding.signUpUserNameEt))
                .addOnCompleteListener(task -> {
                    if (isValidTask(task)) {
                        binding.signUpUserName.setError("Username already taken!");

                        hide(binding.signupProgress);
                        enableTouch(requireActivity().getWindow());
                        return;
                    } else checkEmailExists();
                });
    }

    private void checkEmailExists() {
        queries.checkEmail(getValue(binding.signUpEmailEt))
                .addOnCompleteListener(task -> {
                    if (isValidTask(task)) {
                        binding.signUpEmail.setError("Email already taken!");

                        hide(binding.signupProgress);
                        enableTouch(requireActivity().getWindow());
                        return;
                    } else addUserDataToDB();
                });
    }

    private void addUserDataToDB() {
        queries.addUser(new UserData(
                getValue(binding.signUpUserFirstNameEt),
                getValue(binding.signUpUserLastNameEt),
                getValue(binding.signUpEmailEt),
                getValue(binding.signUpPhoneEt),
                getValue(binding.signUpUserNameEt),
                getValue(binding.signUpPasswordEt)
        )).addOnCompleteListener(task -> {
            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());

            if (task.isSuccessful()) {
                toast(requireContext(), "Successfully Signed up!");
                prefRepository.setLoggedIn(true);
                findNavController(this).navigate(R.id.action_signUpFragment_to_homeFragment);
            } else toast(requireContext(), "Sign up failed. Please try again!");
        });
    }

}