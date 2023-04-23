package pt.isel.pdm.chess4android.ui.screens.online.games

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.databinding.ActivityOnlineBinding
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo
import pt.isel.pdm.chess4android.domain.online.GameState
import pt.isel.pdm.chess4android.domain.online.OnlineGameState
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.views.OnTileTouchListener
import pt.isel.pdm.chess4android.views.Type

private const val GAME_EXTRA = "GameActivity.GameInfoExtra"
private const val LOCAL_PLAYER_EXTRA = "GameActivity.LocalPlayerExtra"

/**
 * Activity for the online game
 */
class OnlineActivity : AppCompatActivity() {

    companion object {
        fun buildIntent(origin: Context, local: Team, challengeInfo: ChallengeInfo) =
            Intent(origin, OnlineActivity::class.java)
                .putExtra(GAME_EXTRA, OnlineGameState(challengeInfo.id, "", GameState.Free, local))
                .putExtra(LOCAL_PLAYER_EXTRA, local.name)
    }

    private val binding : ActivityOnlineBinding by lazy { ActivityOnlineBinding.inflate(layoutInflater) }

    private val localPlayer: Team by lazy {
        val local = intent.getStringExtra(LOCAL_PLAYER_EXTRA)
        if (local != null) Team.valueOf(local)
        else throw IllegalArgumentException("Mandatory extra $LOCAL_PLAYER_EXTRA not present")
    }

    private val initialState: OnlineGameState by lazy {
        intent.getParcelableExtra<OnlineGameState>(GAME_EXTRA) ?:
        throw IllegalArgumentException("Mandatory extra $GAME_EXTRA not present")
    }

    private val viewModel: OnlineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.initGame(initialState, localPlayer)
        binding.finishPhrase.visibility = View.INVISIBLE

        viewModel.game.observe(this) {
            if (binding.finishPhrase.visibility == View.VISIBLE) {
                binding.finishPhrase.visibility = View.INVISIBLE
            }
            val board = it.getOrNull()
            if (board != null) {
                binding.board.drawBoard(board.board)
            }
        }

        viewModel.paint.observe(this) {
            it?.list?.forEach { pos ->
                binding.board.drawSelected(pos, it.type)
            }
        }

        viewModel.xequeMate.observe(this) { result ->
            result.pieces.forEach { pieces ->
                pieces.forEach {
                    binding.board.drawSelected(
                        it.location, Type.XEQUE
                    )
                }
            }
            binding.board.isEnabled = false
            binding.finishPhrase.visibility = View.VISIBLE
            binding.finishPhrase.text =
                if (result.team == Team.BLACK) resources.getString(R.string.black_wins)
                    else resources.getString(R.string.white_wins)
        }

        viewModel.acceptDraw.observe(this) {
            if (it) {
                binding.board.isEnabled = false
                binding.finishPhrase.visibility = View.VISIBLE
                binding.finishPhrase.text = resources.getString(R.string.draw_accepted_black)
            } else {
                binding.finishPhrase.visibility = View.VISIBLE
                binding.finishPhrase.text = resources.getString(R.string.draw_rejected)
            }
        }

        viewModel.draw.observe(this) {
            drawPurpose()
        }

        viewModel.promotion.observe(this) {
            promotionOptions()
        }

        binding.draw.setOnClickListener {
            viewModel.purposeDraw()
        }

        binding.forfeit.setOnClickListener {
            viewModel.forfeit()
        }

        binding.board.setListener(object : OnTileTouchListener {
            override fun onClick(x: Int, y: Int): Boolean {
                viewModel.movePiece(x, y)
                return true
            }
        })
    }

    @SuppressLint("StringFormatMatches")
    private fun drawPurpose() {
        val text =
            if (localPlayer.other == Team.BLACK) resources.getString(R.string.draw_purpose_black)
            else resources.getString(R.string.draw_purpose_white)
        AlertDialog.Builder(this)
            .setTitle(text)
            .setPositiveButton(R.string.accept_game_dialog_ok) { _, _ ->
                viewModel.acceptDraw(true)
            }
            .setNegativeButton(R.string.accept_game_dialog_cancel) { _, _ ->
                viewModel.acceptDraw(false)
            }
            .create()
            .show()
    }

    @SuppressLint("StringFormatMatches")
    private fun promotionOptions() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.promotion))
            .setItems(R.array.promote_items) { _, which ->
                /*
                viewModel.promote(
                    resources.getStringArray(R.array.promote_items)[which]
                )

                 */
            }
            .create()
            .show()
    }
}