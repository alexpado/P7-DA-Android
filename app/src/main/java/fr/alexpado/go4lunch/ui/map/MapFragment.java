package fr.alexpado.go4lunch.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.alexpado.go4lunch.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MapViewModel mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);

        this.binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = this.binding.getRoot();

        TextView textView = this.binding.textHome;
        mapViewModel.getText().observe(this.getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        this.binding = null;
    }

}