package com.valance.medicine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.valance.medicine.databinding.MainFragmentBinding
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment: Fragment() {


    private lateinit var binding: MainFragmentBinding
    lateinit var supabaseClient: SupabaseClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supabaseClient = (requireActivity().application as Medicine).supabaseClient

        launchQuery()
    }

    private fun launchQuery() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val notes = supabaseClient.from("notes").select().decodeList<Notes>()
            Log.d("",notes.toString())
            displayNotes(notes)
        }
    }

    private fun displayNotes(notes: List<Notes>) {
        val notesText = StringBuilder()
        for (note in notes) {
            notesText.append("${note.body}\n")
        }
        binding.notes.text = notesText.toString()
    }

}