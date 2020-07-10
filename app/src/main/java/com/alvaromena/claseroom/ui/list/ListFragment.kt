package com.alvaromena.claseroom.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvaromena.claseroom.ClaseRoom
import com.alvaromena.claseroom.DeudoresRVAdapter
import com.alvaromena.claseroom.MainActivity
import com.alvaromena.claseroom.R
import com.alvaromena.claseroom.model.Deudor
import com.alvaromena.claseroom.model.DeudorDAO


class ListFragment : Fragment() {
    var allDeudores: List<Deudor> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root =  inflater.inflate(R.layout.fragment_list, container, false)
        val rv_deudores=root.findViewById<RecyclerView>(R.id.rv_deudores)

        rv_deudores.layoutManager = LinearLayoutManager(
           requireActivity().applicationContext,
            RecyclerView.VERTICAL,
            false
        )
        rv_deudores.setHasFixedSize(true)

        var deudorDAO: DeudorDAO = ClaseRoom.database.DeudorDAO()
        allDeudores = deudorDAO.getDeudores()

        var deudoresRVAdapter =DeudoresRVAdapter(
            requireActivity().applicationContext,
            allDeudores as ArrayList<Deudor>
        )

        rv_deudores.adapter = deudoresRVAdapter
        deudoresRVAdapter.notifyDataSetChanged()

        return root
    }
}