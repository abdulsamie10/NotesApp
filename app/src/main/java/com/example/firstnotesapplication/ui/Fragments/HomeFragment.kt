package com.example.firstnotesapplication.ui.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.firstnotesapplication.AdsModel
import com.example.firstnotesapplication.Model.Notes
import com.example.firstnotesapplication.R
import com.example.firstnotesapplication.ViewModel.NotesViewModel
import com.example.firstnotesapplication.databinding.FragmentHomeBinding
import com.example.firstnotesapplication.ui.Adapter.NotesAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    val noteViewModel : NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter
    private lateinit var adView : AdView
    lateinit var firestore : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        firestore = FirebaseFirestore.getInstance()
        MobileAds.initialize(requireContext()) {}

        firestore.collection("Ads").document("csDmJ4z3Focf5GmiDjWX").addSnapshotListener { value, error ->
            // Handle the event here
            if (error != null) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            } else {
                val data: AdsModel? = value?.toObject(AdsModel::class.java)
                Log.e("sam", "${data?.adsStatus}")
                if (data != null) {
                    if (data.adsStatus) {
                        val adView = requireView().findViewById<AdView>(R.id.adView)
                        val adRequest = AdRequest.Builder().build()
                        adView.loadAd(adRequest)
                    }
                }
            }
        }

        //get all notes
        noteViewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            binding.rcvAllNotes.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            oldMyNotes = notesList as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(), notesList)
            binding.rcvAllNotes.adapter = adapter
        }

        //filter high notes
        binding.filterHigh.setOnClickListener{
            binding.filterHigh.setBackgroundResource(R.drawable.btn_filter_clicked)
            binding.filterMedium.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterLow.setBackgroundResource(R.drawable.btn_filter_shape)
            noteViewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->
                binding.rcvAllNotes.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            }
        }

        //filter medium notes
        binding.filterMedium.setOnClickListener{
            binding.filterHigh.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterMedium.setBackgroundResource(R.drawable.btn_filter_clicked)
            binding.filterLow.setBackgroundResource(R.drawable.btn_filter_shape)
            noteViewModel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->
                binding.rcvAllNotes.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            }
        }

        //filter low notes
        binding.filterLow.setOnClickListener{
            binding.filterHigh.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterMedium.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterLow.setBackgroundResource(R.drawable.btn_filter_clicked)
            noteViewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->
                binding.rcvAllNotes.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            }
        }

        //filter all notes
        binding.allNotes.setOnClickListener{
            binding.filterHigh.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterMedium.setBackgroundResource(R.drawable.btn_filter_shape)
            binding.filterLow.setBackgroundResource(R.drawable.btn_filter_shape)
            noteViewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
                binding.rcvAllNotes.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            }
        }

        binding.btnAddNotes.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNoteFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val item = menu.findItem(R.id.menu_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Enter text here..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                NotesFiltering(p0)
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                NotesFiltering(p0)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun NotesFiltering(p0: String?) {
        val newFilteredArrayList = arrayListOf<Notes>()
        for(i in oldMyNotes){
            var title = i.title
            title = title.toLowerCase()
            var subtitle = i.subtitle
            subtitle = subtitle.toLowerCase()
            var search = p0?.toLowerCase()
            if(title.contains(search!!) || subtitle.contains(search!!)){
                newFilteredArrayList.add(i)
            }
        }
        adapter.filtering(newFilteredArrayList)
    }
}