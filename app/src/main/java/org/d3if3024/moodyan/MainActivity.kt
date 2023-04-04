package org.d3if3024.moodyan

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.d3if3024.moodyan.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var emot: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.etTanggal.setOnClickListener {
            // Mendapatkan tanggal saat ini
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Membuat instance dari DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this@MainActivity, { view, selectedYear, selectedMonth, selectedDay ->

                    // Membuat objek Date dari tanggal yang dipilih
                    val selectedDate = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay)
                    }.time

                    // Mengecek apakah tanggal yang dipilih melebihi tanggal saat ini
                    if (selectedDate.after(Calendar.getInstance().time)) {
                        Toast.makeText(
                            this@MainActivity, "Tanggal tidak valid!", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Mengubah tanggal yang dipilih menjadi string dengan format tertentu
                        val dateString =
                            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(
                                selectedDate
                            )

                        // Set tanggal yang dipilih ke TextInputEditText
                        binding.etTanggal.setText(dateString)
                    }
                }, year, month, day
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
            val persentase = binding.percentageInp.text.toString().toIntOrNull()
            val tanggal = binding.etTanggal.text.toString()

            //pengecekan inputan kosong atau tidak valid
            if (tanggal.isBlank()) {
                Toast.makeText(this, "Tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (nama.isBlank()) {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (deskPerasaan.isBlank()) {
                Toast.makeText(this, "Deskripsi perasaan tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (persentase == null || persentase < 1 || persentase > 100) {
                Toast.makeText(this, "Persentase harus di antara 1-100", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                // Jika input valid, tampilkan ke TextView
                val resultText =
                    "Nama: $nama\nDeskripsi Perasaan: $deskPerasaan\nPersentase: $persentase%\nTanggal: $tanggal"
                binding.tvResult.text = resultText
            }


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
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
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
                    else -> {
                        emot = R.drawable.ic_default_emoticon
                    }
                }
            }
        }
    }
}




