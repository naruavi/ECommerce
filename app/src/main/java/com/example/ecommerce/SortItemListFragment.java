package com.example.ecommerce;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SortItemListFragment extends BottomSheetDialogFragment {


    public static SortItemListFragment newInstance() {
        return new SortItemListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sort_bottom_sheet, container, false);

        // get the views and attach the listener

        return view;

    }

}
