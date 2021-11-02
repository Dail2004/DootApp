package com.example.dootapp.ui.cread;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.dootapp.MainActivity;
import com.example.dootapp.R;
import com.example.dootapp.databinding.FragmentCreateTaskBinding;
import com.example.dootapp.utils.App;
import com.example.dootapp.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Random;

public class CreateTaskFragment extends Fragment {

    private FragmentCreateTaskBinding binding;
    String userTask;
    String userChooseDate;
    String time;
    String image;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);

        userTask = binding.taskEd.getText().toString();

        binding.setTimeBtn.setOnClickListener(view13 ->
                showDateTimePicker());
        binding.applyBtn.setOnClickListener(view12 -> {

            userTask = binding.taskEd.getText().toString();
            TaskModel model = new TaskModel(getRandomColor(), userTask, userChooseDate + "/" + time, image);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.USER_TASK, model);
            App.getInstance().getDataBase().taskDao().insert(model);
            navController.navigate(R.id.nav_home);
        });
        binding.addImage.setOnClickListener(view1 ->
                mGetContent.launch("image/*"));
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    image = uri.toString();
                    Glide.with(binding.imageView).load(image).centerCrop().into(binding.imageView);
                }
            });


    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @SuppressLint("SetTextI18n")
    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, year, monthOfYear, dayOfMonth) ->
        {
            date.set(year, monthOfYear, dayOfMonth);
            new TimePickerDialog(requireContext(), (view1, hourOfDay, minute) ->
            {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                time = hourOfDay + " : " + minute;
                userChooseDate = date.get(Calendar.MONTH) + "." + date.get(Calendar.DAY_OF_MONTH);

                binding.timeTv.setText(userChooseDate + "/" + time);
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}