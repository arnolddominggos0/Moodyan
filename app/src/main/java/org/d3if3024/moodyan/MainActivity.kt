package org.d3if3024.moodyan

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.d3if3024.moodyan.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var emot: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set OnClickListener pada TextInputEditText untuk meminta input dari pengguna
        binding.etTanggal.setOnClickListener {

            // Mendapatkan tanggal saat ini
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Membuat instance dari DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this@MainActivity,
                { view, selectedYear, selectedMonth, selectedDay ->

                    // Mengubah tanggal yang dipilih menjadi string dengan format tertentu
                    val selectedDate = String.format(
                        "%02d-%02d-%d",
                        selectedDay,
                        selectedMonth + 1,
                        selectedYear
                    )

                    // Set tanggal yang dipilih ke TextInputEditText
                    binding.etTanggal.setText(selectedDate)
                },
                year,
                month,
                day
            )

            // Tampilkan DatePickerDialog
            datePickerDialog.show()
        }

        binding.listPerasaansp.selected { }

        binding.buttonCek.setOnClickListener {
            // Inisialisasi ViewBinding untuk layout yang mengandung TextInputLayout
            binding.tvHasil.setImageResource(emot)
            Log.d("---------", emot.toString())

            val nama = binding.namaInp.text.toString()
            val deskPerasaan = binding.perasaanInp.text.toString()
            val persentase = binding.percentageInp.text.toString().toInt()

            // Menampilkan inputan ke dalam TextView
            val resultText =
                "Nama: $nama\nDeskripsi Perasaan: $deskPerasaan\nPersentase: $persentase%"
            binding.tvResult.text = resultText
        }
    }


        fun Spinner.selected(action: (position: Int) -> Unit) {
        this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                action(position)
                val mood = parent?.getItemAtPosition(position).toString()
                when (mood) {
                    "Senang" -> {
                        emot = R.drawable.ic_happy_emoticon
                    }
                    "Sedih" -> {
                        emot = R.drawable.ic_sad_emoticon
                    }
                    "Marah" -> {
                        emot = R.drawable.ic_angry_emoticon
                    }
                    "Netral" -> {
                        emot = R.drawable.ic_netral_emoticon
                    }
                    "Terhibur" -> {
                        emot = R.drawable.ic_entertained_emoticon
                    }
                }
            }
        }
    }

    private fun formatDate(date: Date): String {
        val today = Date()
        return if (date.after(today)) {
            "Tanggal tidak valid"
        } else {
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
        }
    }
}




