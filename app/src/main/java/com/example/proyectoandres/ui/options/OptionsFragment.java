package com.example.proyectoandres.ui.options;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.proyectoandres.databinding.FragmentOptionsBinding;

public class OptionsFragment extends Fragment {

    private FragmentOptionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OptionsViewModel optionsViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(OptionsViewModel.class);

        binding = FragmentOptionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textOptions;
        optionsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}