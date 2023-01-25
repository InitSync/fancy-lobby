package me.initsync.fancylobby.plugin.tasks;

import me.initsync.fancylobby.api.model.visual.scoreboard.FancyBoard;
import me.initsync.fancylobby.plugin.utils.PlaceholderUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

public class ScoreboardTitleTask extends BukkitRunnable {
	private final FancyBoard board;
	private final List<String> lines;
	
	private int rate;

	public ScoreboardTitleTask(FancyBoard board, List<String> lines, int rate) {
		this.board = Objects.requireNonNull(board, "The board is null.");
		this.lines = Objects.requireNonNull(lines, "The title lines list is null.");
		this.rate = rate;
		
		if (rate > lines.size()) {
			lines = null;
			throw new IndexOutOfBoundsException("The refresh-rate on the title is major than the size of the title list.\n"
				 + "Set the refresh-rate minor than the list size or increment the list content.");
		}
	}
	
	@Override
	public void run() {
		if (rate == lines.size() - 1) rate = 0;
		rate++;
		
		if (board.isDeleted()) return;
		
		board.updateTitle(PlaceholderUtils.parse(board.getPlayer(), lines.get(rate)));
	}
}
