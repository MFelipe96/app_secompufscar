package br.com.secompufscar.secomp_ufscar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.secompufscar.secomp_ufscar.data.Atividade;
import br.com.secompufscar.secomp_ufscar.utilities.ClickListener;
import br.com.secompufscar.secomp_ufscar.utilities.RecyclerTouchListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaAtividades.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaAtividades#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaAtividades extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<Atividade> atividadeList = new ArrayList<>();
    private RecyclerView recycler_atividades;
    private AtividadesAdapter aAdapter;

    public ListaAtividades() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaAtividades.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaAtividades newInstance(String param1, String param2) {
        ListaAtividades fragment = new ListaAtividades();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        prepararAtividades();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_atividades, container, false);
        recycler_atividades = (RecyclerView) view.findViewById(R.id.recycler_atividades);
        //TODO: É necessário arrumar a parte de carregamento dos fragmentos
        aAdapter = new AtividadesAdapter(atividadeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycler_atividades.setLayoutManager(mLayoutManager);
//        TODO: Arrumar o problema que está dando aqui
        recycler_atividades.setItemAnimator(new DefaultItemAnimator());

        recycler_atividades.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recycler_atividades, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Atividade atividade = atividadeList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), atividade.nome + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_atividades.setAdapter(aAdapter);

        return view;
    }

    public void prepararAtividades(){
        Atividade aux = new Atividade("Palestra 1", "Bento Prado", "Palestra");
        atividadeList.add(aux);
        aux = new Atividade("Palestra 2", "Bento Prado", "Palestra");
        atividadeList.add(aux);
        aux = new Atividade("Palestra 3", "Bento Prado", "Palestra");
        atividadeList.add(aux);
        aux = new Atividade("Palestra 4", "Bento Prado", "Palestra");
        atividadeList.add(aux);
        aux = new Atividade("Palestra 5", "Bento Prado", "Palestra");
        atividadeList.add(aux);
        aux = new Atividade("Palestra 6", "Bento Prado", "Palestra");
        atividadeList.add(aux);
        aux = new Atividade("Palestra 7", "Bento Prado", "Palestra");
        atividadeList.add(aux);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
