package fr.alexpado.go4lunch.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.alexpado.go4lunch.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SlideshowViewModel slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);

        this.binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = this.binding.getRoot();

        TextView textView = this.binding.textSlideshow;
        slideshowViewModel.getText().observe(this.getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        this.binding = null;
    }

}