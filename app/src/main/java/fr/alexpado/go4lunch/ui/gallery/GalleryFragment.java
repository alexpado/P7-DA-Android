package fr.alexpado.go4lunch.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.alexpado.go4lunch.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        GalleryViewModel galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        this.binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = this.binding.getRoot();

        TextView textView = this.binding.textGallery;
        galleryViewModel.getText().observe(this.getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        this.binding = null;
    }

}