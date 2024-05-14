package fr.alexpado.go4lunch.ui.workmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.alexpado.go4lunch.databinding.FragmentWorkmatesBinding;

public class WorkmatesFragment extends Fragment {

    private FragmentWorkmatesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        WorkmatesViewModel workmatesViewModel = new ViewModelProvider(this).get(WorkmatesViewModel.class);

        this.binding = FragmentWorkmatesBinding.inflate(inflater, container, false);
        View root = this.binding.getRoot();

        TextView textView = this.binding.textSlideshow;
        workmatesViewModel.getText().observe(this.getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        this.binding = null;
    }

}