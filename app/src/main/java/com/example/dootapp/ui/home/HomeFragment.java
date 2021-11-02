package com.example.dootapp.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dootapp.R;
import com.example.dootapp.adapter.TaskAdapter;
import com.example.dootapp.databinding.FragmentHomeBinding;
import com.example.dootapp.ui.cread.TaskModel;
import com.example.dootapp.utils.App;
import com.example.dootapp.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TaskAdapter adapter;
    TaskModel model;
    ArrayList<TaskModel> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);

        if (getArguments() != null) {
            model = (TaskModel) getArguments().getSerializable(Constants.USER_TASK);
            list.add(model);
        }
//        getDataFromDataBase();

        binding.fab.setOnClickListener(view1 ->
                navController.navigate(R.id.create_task_fragment));

//        initAdapter();
//        setupListener();
    }

    private void setupListener() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Вы точно хотите удалить")
                        .setPositiveButton("Да", ((dialog, which) -> {
                            App.getInstance().getDataBase().taskDao().delete(adapter.delete(viewHolder.getAdapterPosition()));
                            adapter.notifyDataSetChanged();
                        }));
                builder.setNegativeButton("Нет", (dialog, which) ->
                        adapter.notifyDataSetChanged()).show();
            }
        }).attachToRecyclerView(binding.taskRecycler);


    }

    private ArrayList<TaskModel> getDataFromDataBase() {
        return (ArrayList<TaskModel>) App.getInstance().getDataBase().taskDao().getAll();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initAdapter() {
        TaskAdapter adapter = new TaskAdapter(getDataFromDataBase());
        binding.taskRecycler.setAdapter(adapter);
        this.adapter = adapter;
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}