package com.nandaiqbalh.notetaking.helper.noteadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nandaiqbalh.notetaking.data.local.note.NoteEntity
import com.nandaiqbalh.notetaking.databinding.RowItemNotesBinding


class NoteAdapter(private val listener: NoteItemClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var items: MutableList<NoteEntity> = mutableListOf()

    fun setItems(items: List<NoteEntity>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<NoteEntity>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun editItem(item: NoteEntity, position: Int) {
        this.items[position] = item
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            RowItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    class NoteViewHolder(
        private val binding: RowItemNotesBinding,
        private val listener: NoteItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: NoteEntity) {
            with(item) {
                binding.tvTitle.text = title
                binding.tvDescription.text = description
                binding.tvNoteId.text = noteId.toString()

                itemView.setOnClickListener { listener.onItemClicked(this) }
                binding.ivEdit.setOnClickListener { listener.onEditClicked(item, adapterPosition) }
                binding.ivDelete.setOnClickListener { listener.onDeleteClicked(this) }
            }
        }
    }

}

interface NoteItemClickListener {
    fun onItemClicked(item: NoteEntity)
    fun onEditClicked(item: NoteEntity, position: Int)
    fun onDeleteClicked(item: NoteEntity)
}