package com.nandaiqbalh.notetaking.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.nandaiqbalh.notetaking.R
import com.nandaiqbalh.notetaking.data.local.note.NoteEntity
import com.nandaiqbalh.notetaking.databinding.CustomDialogNoteBinding
import com.nandaiqbalh.notetaking.di.ServiceLocator
import com.nandaiqbalh.notetaking.util.viewModelFactory
import com.nandaiqbalh.notetaking.wrapper.Resource

class NoteCustomDialog(private val initialId: Long? = null): DialogFragment() {

    private var _binding: CustomDialogNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModelFactory {
        NoteViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }

    private var listener : OnItemChangedListener? = null

    fun setListener(listener: OnItemChangedListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        _binding = CustomDialogNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeData()
        setOnClickListener()
        getInitialData()
    }

    private fun setOnClickListener() {
        binding.btnSubmit.setOnClickListener {
            saveNote()
        }
    }

    private fun getInitialData() {
        if (isEditAction()) {
            initialId?.let {
                viewModel.getNoteById(it)
            }
        }
    }

    private fun saveNote() {
        if (isEditAction()) {
            viewModel.updateNote(parseFormIntoEntity())
        } else {
            viewModel.insertNote(parseFormIntoEntity())
        }
    }

    private fun isEditAction(): Boolean {
        return initialId != null
    }

    private fun parseFormIntoEntity(): NoteEntity {
        return NoteEntity(
            title = binding.etTitle.text.toString(),
            description = binding.etDescription.text.toString()
        ).apply {
            initialId?.let {
                noteId = it
            }
        }
    }

    private fun observeData() {
        viewModel.getNoteResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> bindDataToForm(it.payload)
                is Resource.Error -> {
                    dismiss()
                    Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.updateResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    dismiss()
                    listener?.onItemChanged()
                    Toast.makeText(requireContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    dismiss()
                    Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.insertResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    dismiss()
                    listener?.onItemChanged()
                    Toast.makeText(requireContext(), "Successfully to input!", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    dismiss()
                    Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun bindDataToForm(note: NoteEntity?) {
        note?.let {
            binding.etTitle.setText(note.title)
            binding.etDescription.setText(note.description)
        }
    }

    private fun initView() {
        if (isEditAction()) {
            binding.tvTitle.text = "Edit note"
            binding.btnSubmit.text = "Simpan"
        } else {
            binding.tvTitle.text = "Masukkan note"
            binding.btnSubmit.text = "Input"
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface OnItemChangedListener {
    fun onItemChanged()
}