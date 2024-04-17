package fr.alexpado.go4lunch.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.alexpado.go4lunch.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        this.binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = this.binding.getRoot();

        TextView textView = this.binding.textHome;
        homeViewModel.getText().observe(this.getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        this.binding = null;
    }

}