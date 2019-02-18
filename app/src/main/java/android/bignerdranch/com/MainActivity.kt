package android.bignerdranch.com

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

private var Bundle.characterData
    get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
    set(value) = putSerializable(CHARACTER_DATA_KEY,value)

class MainActivity : AppCompatActivity() {
    private var characterData = CharacterGenerator.generate()

    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        outState.characterData = characterData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        characterData = savedInstanceState?.characterData ?: CharacterGenerator.generate()
        //setting a click listener
        generateButton.setOnClickListener{
            GlobalScope.launch (Dispatchers.Main){
                characterData = fetchCharacterData().await()
                displayCharacterData()
            }
        }
        //wiring up views
        displayCharacterData()
    }

    private fun displayCharacterData(){
        characterData.run {
            nameTextView.text = name
            raceTextView.text = race
            dexterityTextView.text = dex
            wisdomTextView.text = wis
            strengthTextView.text = str
        }
    }
}
