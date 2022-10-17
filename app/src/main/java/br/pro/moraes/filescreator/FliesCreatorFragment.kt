package br.pro.moraes.filescreator

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.pro.moraes.filescreator.databinding.FragmentFliesCreatorBinding
import java.io.File

class FliesCreatorFragment : Fragment() {

    companion object {
        fun newInstance() = FliesCreatorFragment()
    }

    private lateinit var viewModel: FliesCreatorViewModel
    private var _binding: FragmentFliesCreatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFliesCreatorBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCreateFile.setOnClickListener {
            val (fileName, fileContent) = fileData()
            val file = createEditFile(fileName, fileContent, Context.MODE_PRIVATE)
            setTextOnView(fileName, file)
        }

        binding.buttonAddInFile.setOnClickListener {
            val (fileName, fileContent) = fileData()
            val file = createEditFile(fileName, fileContent, Context.MODE_APPEND)
            setTextOnView(fileName, file)
        }
    }

    private fun setTextOnView(fileName: String, file: File) {
        binding.textViewFileName.setText(fileName)
        binding.textViewFileContent.setText(file.readText())
    }

    private fun fileData(): Pair<String, String> {
        val fileName = binding.editTextTextCreateFileName.text.toString()
        val fileContent = binding.editTextTextCreateContent.text.toString()
        return Pair(fileName, fileContent)
    }

    private fun createEditFile(fileName: String, fileContent: String, mode: Int): File {
        //cria arquivos e sobreescreve arquivo de mesmo nome
        //mode define se sobrescreverá ou adicionará
        val file = File(requireContext().filesDir, fileName)
        requireContext().openFileOutput(fileName, mode).use {
            it.write(fileContent.toByteArray())
        }
        return file
    }
}