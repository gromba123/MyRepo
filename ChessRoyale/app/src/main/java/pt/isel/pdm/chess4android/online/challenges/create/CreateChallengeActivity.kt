package pt.isel.pdm.chess4android.online.challenges.create

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import pt.isel.pdm.chess4android.APP_TAG
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.databinding.ActivityCreateChallengeBinding
import pt.isel.pdm.chess4android.offline.pieces.Team
import pt.isel.pdm.chess4android.online.games.OnlineActivity

/**
 * Activity to create a challenge
 */
class CreateChallengeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreateChallengeBinding.inflate(layoutInflater) }
    private val viewModel: CreateChallengeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.created.observe(this) {
            if (it == null) displayCreateChallenge()
            else it.onFailure { displayError() }.onSuccess {
                displayWaitingForChallenger()
            }
        }

        viewModel.accepted.observe(this) {
            if (it == true) {
                Log.v(APP_TAG, "Someone accepted our challenge")
                viewModel.created.value?.onSuccess { challenge ->
                    val intent = OnlineActivity.buildIntent(
                        origin = this,
                        local = Team.firstToMove,
                        challengeInfo = challenge
                    )
                    startActivity(intent)
                }
            }
        }

        binding.action.setOnClickListener {
            if (viewModel.created.value == null)
                viewModel.createChallenge(
                    binding.name.text.toString(),
                    binding.message.text.toString()
                )
            else viewModel.removeChallenge()
        }
    }

    /**
     * Displays the screen in its Create challenge state
     */
    private fun displayCreateChallenge() {
        binding.action.text = getString(R.string.create_game_button_label)
        with(binding.name) { text.clear(); isEnabled = true }
        with(binding.message) { text.clear(); isEnabled = true }
        binding.loading.isVisible = false
        binding.waitingMessage.isVisible = false
    }

    /**
     * Displays the screen in its Waiting for challenge state
     */
    private fun displayWaitingForChallenger() {
        binding.action.text = getString(R.string.cancel_game_button_label)
        binding.name.isEnabled = false
        binding.message.isEnabled = false
        binding.loading.isVisible = true
        binding.waitingMessage.isVisible = true
    }

    /**
     * Displays the screen in its error creating challenge state
     */
    private fun displayError() {
        displayCreateChallenge()
        Toast
            .makeText(this, R.string.error_creating_game, Toast.LENGTH_LONG)
            .show()
    }
}