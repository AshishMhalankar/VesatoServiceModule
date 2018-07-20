package com.vesatago.userapp.modules;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vesatago.userapp.R;
import com.vesatago.userapp.modules.vehicle.VehicleHireActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {


    RelativeLayout Relative_vehical,Relative_tractor;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        v = inflater.inflate(R.layout.activity_home, container, false);


        init();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("भाड्याने द्या");
    }





    private void init() {
        Relative_vehical=v.findViewById(R.id.Relative_vehical);
        Relative_tractor=v.findViewById(R.id.Relative_tractor);

        Relative_vehical.setOnClickListener(this);
        Relative_tractor.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Relative_vehical:

//                DialogRentHireOption("vehical");
                Intent intent=new Intent(getActivity(), VehicleHireActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.Relative_tractor:

                //DialogRentHireOption("tractor");
                break;


        }
    }


    private void DialogRentHireOption(final String s) {

        Dialog dialog_RentHireOption= new Dialog(getContext());

        dialog_RentHireOption.setContentView(R.layout.dialo_select_rent_hire);
        //  dialog_prebookAlertPopup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //   dialog_EquipmentRegistration.setCancelable(false);

        TextView tv_rent=dialog_RentHireOption.findViewById(R.id.tv_rent);
        TextView tv_hire=dialog_RentHireOption.findViewById(R.id.tv_hire);

        tv_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s.equals("vehical")){
                    Intent intent=new Intent(getActivity(), VehicleHireActivity.class);
                    getActivity().startActivity(intent);
                }
            }
        });




        dialog_RentHireOption.show();
    }
}
