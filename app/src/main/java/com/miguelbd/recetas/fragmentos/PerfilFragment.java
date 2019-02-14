package com.miguelbd.recetas.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.miguelbd.recetas.R;

public class PerfilFragment extends Fragment {

    private EditText edtUser;
    private EditText edtPass;
    private EditText edtName;
    private EditText edtLastName;
    private EditText edtBirthdate;
    private EditText edtEmail;
    private EditText edtTelephone;
    private Button   btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

}
