/**
 * Copyright (C) 2016-2017 Anand M Joseph.
 */
package conway.anand.myconway.coustomwidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import conway.anand.myconway.app.AppConstants;

/**
 * This View handles drawing of the single life cycles.
 * 
 * @author anandmjoseph.
 * @date 28/06/16
 */
public final class AndroidLifeView extends View {

	/**
	 * Static variables storing map and cell size.
	 */
	private static final int	CELL_SIZE	= 20;

	/**
	 * Height of Screen. Will be used for calculating size of map.
	 */
	private static final int	HEIGHT		= AppConstants.getViewportHeight() / CELL_SIZE;

	/**
	 * Width of Screen. Will be used for calculating size of map.
	 */
	private static final int	WIDTH		= AppConstants.getViewportWidth() / CELL_SIZE;

	/**
	 * The RuleSet that will be used.
	 */
	private static int[]		gameRule;

	/**
	 * Foreground paint colour. Will change dynamicly for each cell, depending on its position on map.
	 */
	private Paint				foreground	= new Paint();

	/**
	 * Background colour.
	 */
	private Paint				background	= new Paint();

	/**
	 * Corner colours for calculation the gradient.
	 */
	private int[][]				red, green, blue;

	/**
	 * The GameBord. Will hold the current evolution.
	 */
	private boolean[][]			map;

	/**
	 * Semaphore, to controll main draw method.
	 */
	private static boolean		LOCKED		= false;

	/**
	 * Default Constructor.
	 * 
	 * @param context
	 *            Context
	 */
	public AndroidLifeView(Context context) {
		super(context);
		init();
	}

	/**
	 * Constructor with custom AttributeSet.
	 * 
	 * @param context
	 *            Context
	 * @param attrs
	 *            Custom AttributeSet
	 */
	public AndroidLifeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * Constructor with custom AttributeSet and defStyle.
	 * 
	 * @param context
	 *            Context
	 * @param attrs
	 *            Custom AttributeSet
	 * @param defSytle
	 *            Custom defStyle
	 */
	public AndroidLifeView(Context context, AttributeSet attrs, int defSytle) {
		super(context, attrs, defSytle);
		init();
	}

	/**
	 * This method is used by all constructors. It will initiate all needed variables.
	 */
	private void init() {
		// foreground.setColor(Color.GREEN);
		background.setARGB(255, 45, 45, 45);
		initColorArrays();
		initMap();
		initGameRules();
	}

	/**
	 * This method will callculate a gradient colour map.
	 */
	private void initColorArrays() {
		red = new int[HEIGHT][WIDTH];
		green = new int[HEIGHT][WIDTH];
		blue = new int[HEIGHT][WIDTH];

		// colour 1
		int red1 = 255;
		int green1 = 0;
		int blue1 = 0;

		// colour 2
		int red2 = 255;
		int green2 = 255;
		int blue2 = 0;

		// colour 3
		int red3 = 0;
		int green3 = 0;
		int blue3 = 255;

		// colour 4
		int red4 = 0;
		int green4 = 255;
		int blue4 = 0;

		int diff_red2;
		int diff_green2;
		int diff_blue2;

		for (int h = 0; h < HEIGHT; h++)
			for (int w = 0; w < WIDTH; w++) {
				red[h][w] = red1 + ((red2 - red1) * h / HEIGHT);
				diff_red2 = red3 + ((red4 - red3) * h / HEIGHT);
				red[h][w] += ((diff_red2 - red[h][w]) * w / WIDTH);

				blue[h][w] = blue1 + ((blue2 - blue1) * h / HEIGHT);
				diff_blue2 = blue3 + ((blue4 - blue3) * h / HEIGHT);
				blue[h][w] += ((diff_blue2 - blue[h][w]) * w / WIDTH);

				green[h][w] = green1 + ((green2 - green1) * h / HEIGHT);
				diff_green2 = green3 + ((green4 - green3) * h / HEIGHT);
				green[h][w] += ((diff_green2 - green[h][w]) * w / WIDTH);
			}
	}

	/**
	 * This will draw our Game of Life.
	 * 
	 * @param canvas
	 *            Canvas to draw on.
	 */
	public void onDraw(Canvas canvas) {
		canvas.drawRect((float) 0, (float) 0, (float) getWidth(), (float) getHeight(), background);

		for (int h = 0; h < HEIGHT; h++)
			for (int w = 0; w < WIDTH; w++)
				if (map[h][w]) {
					foreground.setARGB(255, red[h][w], green[h][w], blue[h][w]);
					canvas.drawRect(w * CELL_SIZE, h * CELL_SIZE, (w * CELL_SIZE) + CELL_SIZE, (h * CELL_SIZE) + CELL_SIZE, foreground);
				}
	}

	/**
	 * Method to draw a boolean array.
	 * 
	 * @param m
	 *            the map to draw.
	 */
	private void drawMap(boolean[][] m) {
		this.map = m;
		postInvalidate();
	}

	/**
	 * Method to initiate games RuleSet.
	 */
	private void initGameRules() {
		// set Conway as initial rule
		// notice: we don't use the database here, because all rules can be deleted
		// and we want the conway rule to be the initial rule come what ever will!
		gameRule = new int[9];
		gameRule[0] = AppConstants.DEATH_RULE;
		gameRule[1] = AppConstants.DEATH_RULE;
		gameRule[2] = AppConstants.UNDEFINED;
		gameRule[3] = AppConstants.BIRTH_RULE;
		gameRule[4] = AppConstants.DEATH_RULE;
		gameRule[5] = AppConstants.DEATH_RULE;
		gameRule[6] = AppConstants.DEATH_RULE;
		gameRule[7] = AppConstants.DEATH_RULE;
		gameRule[8] = AppConstants.DEATH_RULE;
		AppConstants.setRuleSet(gameRule);
	}

	/**
	 * This method can be used to update the games RuleSet.
	 */
	private void updateGameRules() {
		gameRule = AppConstants.getRuleSet();
	}

	/**
	 * This method will initiate our first map.
	 */
	private void initMap() {

		map = new boolean[HEIGHT][WIDTH];

		int xOffset = HEIGHT / 2 - 10;
		int yOffset = WIDTH / 2 - 7;

		/*
		 * Printing Logo screen (16 * 14 cells)
		 */
		map[xOffset + 0][yOffset + 0] = true;
		map[xOffset + 0][yOffset + 1] = true;
		map[xOffset + 0][yOffset + 2] = true;
		map[xOffset + 0][yOffset + 4] = true;
		map[xOffset + 0][yOffset + 5] = true;
		map[xOffset + 0][yOffset + 6] = true;
		map[xOffset + 0][yOffset + 8] = true;
		map[xOffset + 0][yOffset + 10] = true;
		map[xOffset + 0][yOffset + 12] = true;
		map[xOffset + 0][yOffset + 13] = true;
		map[xOffset + 0][yOffset + 14] = true;
		map[xOffset + 1][yOffset + 0] = true;
		map[xOffset + 1][yOffset + 4] = true;
		map[xOffset + 1][yOffset + 6] = true;
		map[xOffset + 1][yOffset + 8] = true;
		map[xOffset + 1][yOffset + 9] = true;
		map[xOffset + 1][yOffset + 10] = true;
		map[xOffset + 1][yOffset + 12] = true;
		map[xOffset + 2][yOffset + 0] = true;
		map[xOffset + 2][yOffset + 2] = true;
		map[xOffset + 2][yOffset + 4] = true;
		map[xOffset + 2][yOffset + 5] = true;
		map[xOffset + 2][yOffset + 6] = true;
		map[xOffset + 2][yOffset + 8] = true;
		map[xOffset + 2][yOffset + 10] = true;
		map[xOffset + 2][yOffset + 12] = true;
		map[xOffset + 2][yOffset + 13] = true;
		map[xOffset + 3][yOffset + 0] = true;
		map[xOffset + 3][yOffset + 2] = true;
		map[xOffset + 3][yOffset + 4] = true;
		map[xOffset + 3][yOffset + 6] = true;
		map[xOffset + 3][yOffset + 8] = true;
		map[xOffset + 3][yOffset + 10] = true;
		map[xOffset + 3][yOffset + 12] = true;
		map[xOffset + 4][yOffset + 0] = true;
		map[xOffset + 4][yOffset + 1] = true;
		map[xOffset + 4][yOffset + 2] = true;
		map[xOffset + 4][yOffset + 4] = true;
		map[xOffset + 4][yOffset + 6] = true;
		map[xOffset + 4][yOffset + 8] = true;
		map[xOffset + 4][yOffset + 10] = true;
		map[xOffset + 4][yOffset + 12] = true;
		map[xOffset + 4][yOffset + 13] = true;
		map[xOffset + 4][yOffset + 14] = true;
		map[xOffset + 6][yOffset + 4] = true;
		map[xOffset + 6][yOffset + 5] = true;
		map[xOffset + 6][yOffset + 6] = true;
		map[xOffset + 6][yOffset + 8] = true;
		map[xOffset + 6][yOffset + 9] = true;
		map[xOffset + 6][yOffset + 10] = true;
		map[xOffset + 7][yOffset + 4] = true;
		map[xOffset + 7][yOffset + 6] = true;
		map[xOffset + 7][yOffset + 8] = true;
		map[xOffset + 8][yOffset + 4] = true;
		map[xOffset + 8][yOffset + 6] = true;
		map[xOffset + 8][yOffset + 8] = true;
		map[xOffset + 8][yOffset + 9] = true;
		map[xOffset + 9][yOffset + 4] = true;
		map[xOffset + 9][yOffset + 6] = true;
		map[xOffset + 9][yOffset + 8] = true;
		map[xOffset + 10][yOffset + 4] = true;
		map[xOffset + 10][yOffset + 5] = true;
		map[xOffset + 10][yOffset + 6] = true;
		map[xOffset + 10][yOffset + 8] = true;
		map[xOffset + 12][yOffset + 0] = true;
		map[xOffset + 12][yOffset + 4] = true;
		map[xOffset + 12][yOffset + 5] = true;
		map[xOffset + 12][yOffset + 6] = true;
		map[xOffset + 12][yOffset + 8] = true;
		map[xOffset + 12][yOffset + 9] = true;
		map[xOffset + 12][yOffset + 10] = true;
		map[xOffset + 12][yOffset + 12] = true;
		map[xOffset + 12][yOffset + 13] = true;
		map[xOffset + 12][yOffset + 14] = true;
		map[xOffset + 13][yOffset + 0] = true;
		map[xOffset + 13][yOffset + 5] = true;
		map[xOffset + 13][yOffset + 8] = true;
		map[xOffset + 13][yOffset + 12] = true;
		map[xOffset + 14][yOffset + 0] = true;
		map[xOffset + 14][yOffset + 5] = true;
		map[xOffset + 14][yOffset + 8] = true;
		map[xOffset + 14][yOffset + 9] = true;
		map[xOffset + 14][yOffset + 12] = true;
		map[xOffset + 14][yOffset + 13] = true;
		map[xOffset + 15][yOffset + 0] = true;
		map[xOffset + 15][yOffset + 5] = true;
		map[xOffset + 15][yOffset + 8] = true;
		map[xOffset + 15][yOffset + 12] = true;
		map[xOffset + 16][yOffset + 0] = true;
		map[xOffset + 16][yOffset + 1] = true;
		map[xOffset + 16][yOffset + 2] = true;
		map[xOffset + 16][yOffset + 4] = true;
		map[xOffset + 16][yOffset + 5] = true;
		map[xOffset + 16][yOffset + 6] = true;
		map[xOffset + 16][yOffset + 8] = true;
		map[xOffset + 16][yOffset + 12] = true;
		map[xOffset + 16][yOffset + 13] = true;
		map[xOffset + 16][yOffset + 14] = true;

		// ====== Dot line

		map[xOffset + 20][(yOffset - 2) + 0] = true;
		map[xOffset + 20][(yOffset - 4) + 0] = true;

		map[xOffset + 20][yOffset + 0] = true;
		map[xOffset + 20][yOffset + 2] = true;
		map[xOffset + 20][yOffset + 4] = true;
		map[xOffset + 20][yOffset + 6] = true;
		map[xOffset + 20][yOffset + 8] = true;
		map[xOffset + 20][yOffset + 10] = true;
		map[xOffset + 20][yOffset + 12] = true;
		map[xOffset + 20][yOffset + 14] = true;
		map[xOffset + 20][yOffset + 16] = true;
		map[xOffset + 20][yOffset + 18] = true;
		map[xOffset + 20][yOffset + 20] = true;
		map[xOffset + 20][yOffset + 22] = true;
		map[xOffset + 20][yOffset + 24] = true;
		map[xOffset + 20][yOffset + 26] = true;

		map[xOffset + 22][(yOffset - 4) + 0] = true;
		map[xOffset + 20][(yOffset - 4) + 0] = true;
		map[xOffset + 24][(yOffset - 4) + 0] = true;
		map[xOffset + 26][(yOffset - 4) + 0] = true;
		map[xOffset + 28][(yOffset - 4) + 0] = true;
		map[xOffset + 30][(yOffset - 4) + 0] = true;

		map[xOffset + 32][(yOffset - 4) + 0] = true;
		map[xOffset + 32][(yOffset - 2) + 0] = true;

		map[xOffset + 32][yOffset + 0] = true;
		map[xOffset + 32][yOffset + 2] = true;
		map[xOffset + 32][yOffset + 4] = true;
		map[xOffset + 32][yOffset + 6] = true;
		map[xOffset + 32][yOffset + 8] = true;
		map[xOffset + 32][yOffset + 10] = true;
		map[xOffset + 32][yOffset + 12] = true;
		map[xOffset + 32][yOffset + 14] = true;
		map[xOffset + 32][yOffset + 16] = true;
		map[xOffset + 32][yOffset + 18] = true;
		map[xOffset + 32][yOffset + 20] = true;
		map[xOffset + 32][yOffset + 22] = true;
		map[xOffset + 32][yOffset + 24] = true;
		map[xOffset + 32][yOffset + 26] = true;

		map[xOffset + 22][yOffset + 26] = true;
		map[xOffset + 20][yOffset + 26] = true;
		map[xOffset + 24][yOffset + 26] = true;
		map[xOffset + 26][yOffset + 26] = true;
		map[xOffset + 28][yOffset + 26] = true;
		map[xOffset + 30][yOffset + 26] = true;

		// ====== Developer Name

		// ====== Letter A Start

		map[xOffset + 24][yOffset + 0] = true;
		map[xOffset + 25][yOffset + 0] = true;
		map[xOffset + 26][yOffset + 0] = true;
		map[xOffset + 27][yOffset + 0] = true;
		map[xOffset + 28][yOffset + 0] = true;
		map[xOffset + 29][yOffset + 0] = true;

		map[xOffset + 24][yOffset + 1] = true;
		map[xOffset + 26][yOffset + 1] = true;

		map[xOffset + 24][yOffset + 2] = true;
		map[xOffset + 25][yOffset + 2] = true;
		map[xOffset + 26][yOffset + 2] = true;
		map[xOffset + 27][yOffset + 2] = true;
		map[xOffset + 28][yOffset + 2] = true;
		map[xOffset + 29][yOffset + 2] = true;

		// ====== Letter A End

		// ====== Letter N Start

		map[xOffset + 24][yOffset + 4] = true;
		map[xOffset + 25][yOffset + 4] = true;
		map[xOffset + 26][yOffset + 4] = true;
		map[xOffset + 27][yOffset + 4] = true;
		map[xOffset + 28][yOffset + 4] = true;
		map[xOffset + 29][yOffset + 4] = true;

		map[xOffset + 24][yOffset + 5] = true;

		map[xOffset + 24][yOffset + 6] = true;
		map[xOffset + 25][yOffset + 6] = true;
		map[xOffset + 26][yOffset + 6] = true;
		map[xOffset + 27][yOffset + 6] = true;
		map[xOffset + 28][yOffset + 6] = true;
		map[xOffset + 29][yOffset + 6] = true;

		map[xOffset + 29][yOffset + 7] = true;

		map[xOffset + 24][yOffset + 8] = true;
		map[xOffset + 25][yOffset + 8] = true;
		map[xOffset + 26][yOffset + 8] = true;
		map[xOffset + 27][yOffset + 8] = true;
		map[xOffset + 28][yOffset + 8] = true;
		map[xOffset + 29][yOffset + 8] = true;

		// ====== Letter N END

		// ====== Letter A Start

		map[xOffset + 24][yOffset + 10] = true;
		map[xOffset + 25][yOffset + 10] = true;
		map[xOffset + 26][yOffset + 10] = true;
		map[xOffset + 27][yOffset + 10] = true;
		map[xOffset + 28][yOffset + 10] = true;
		map[xOffset + 29][yOffset + 10] = true;

		map[xOffset + 24][yOffset + 11] = true;
		map[xOffset + 26][yOffset + 11] = true;

		map[xOffset + 24][yOffset + 12] = true;
		map[xOffset + 25][yOffset + 12] = true;
		map[xOffset + 26][yOffset + 12] = true;
		map[xOffset + 27][yOffset + 12] = true;
		map[xOffset + 28][yOffset + 12] = true;
		map[xOffset + 29][yOffset + 12] = true;

		// ====== Letter A End

		// ====== Letter N Start

		map[xOffset + 24][yOffset + 14] = true;
		map[xOffset + 25][yOffset + 14] = true;
		map[xOffset + 26][yOffset + 14] = true;
		map[xOffset + 27][yOffset + 14] = true;
		map[xOffset + 28][yOffset + 14] = true;
		map[xOffset + 29][yOffset + 14] = true;

		map[xOffset + 24][yOffset + 15] = true;

		map[xOffset + 24][yOffset + 16] = true;
		map[xOffset + 25][yOffset + 16] = true;
		map[xOffset + 26][yOffset + 16] = true;
		map[xOffset + 27][yOffset + 16] = true;
		map[xOffset + 28][yOffset + 16] = true;
		map[xOffset + 29][yOffset + 16] = true;

		map[xOffset + 29][yOffset + 17] = true;

		map[xOffset + 24][yOffset + 18] = true;
		map[xOffset + 25][yOffset + 18] = true;
		map[xOffset + 26][yOffset + 18] = true;
		map[xOffset + 27][yOffset + 18] = true;
		map[xOffset + 28][yOffset + 18] = true;
		map[xOffset + 29][yOffset + 18] = true;

		// ====== Letter N END

		// ====== Letter D Start

		map[xOffset + 24][yOffset + 20] = true;
		map[xOffset + 25][yOffset + 20] = true;
		map[xOffset + 26][yOffset + 20] = true;
		map[xOffset + 27][yOffset + 20] = true;
		map[xOffset + 28][yOffset + 20] = true;
		map[xOffset + 29][yOffset + 20] = true;

		map[xOffset + 24][yOffset + 21] = true;
		map[xOffset + 29][yOffset + 21] = true;

		map[xOffset + 24][yOffset + 22] = true;
		map[xOffset + 25][yOffset + 22] = true;
		map[xOffset + 26][yOffset + 22] = true;
		map[xOffset + 27][yOffset + 22] = true;
		map[xOffset + 28][yOffset + 22] = true;
		map[xOffset + 29][yOffset + 22] = true;

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		int tempY = (int) event.getY() / CELL_SIZE;
		int tempX = (int) event.getX() / CELL_SIZE;

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!map[tempY][tempX])
					map[tempY][tempX] = true;
				else
					map[tempY][tempX] = false;
				break;
		}

		invalidate();
		return true;
	}

	/**
	 * Semaphore locker.
	 */
	public void lock() {
		updateGameRules();
		LOCKED = true;
	}

	/**
	 * Semaphore unlocker.
	 */
	public void unlock() {
		LOCKED = false;
	}

	/**
	 * This will return the current map
	 * 
	 * @return Game of Life map.
	 */
	public boolean[][] getMap() {
		return map;
	}

	/**
	 * MAIN GAME LOGIC. This method will calculate and draw the entire Game of Life.
	 * 
	 * @param rule
	 *            Game of Life RuleSet.
	 */
	public void runLoop() {
		new Thread(new Runnable() {

			/**
			 * This will apply the game rule considering the neigbourhood.
			 * 
			 * @param region
			 *            neigbourhood
			 * @return state of cell in next cycle
			 */
			public boolean apply(boolean[][] region) {
				/*
				 * initial status of cell.
				 */
				boolean status = region[1][1];

				/*
				 * counting neighbours.
				 */
				int neighbours = 0;
				if (status)
					neighbours--;
				for (int x = 0; x < 3; x++)
					for (int y = 0; y < 3; y++)
						if (region[x][y])
							neighbours++;

				/*
				 * apply rules
				 */
				int destiny = gameRule[neighbours];

				if (destiny == AppConstants.DEATH_RULE)
					return false;

				if (destiny == AppConstants.BIRTH_RULE)
					return true;

				/*
				 * destiny is undefined.
				 */
				return status;
			}

			@Override
			public void run() {

				/*
				 * This will be the neigbourhood of interest.
				 */
				boolean[][] region = new boolean[3][3];

				/*
				 * The Animation loop.
				 */
				while (LOCKED) {

					/*
					 * Generation that will life the next cycle.
					 */
					boolean[][] nextGen = new boolean[HEIGHT][WIDTH];

					/*
					 * 00 01 02 10 11 12 <- 11 is our Cell 20 21 22 everything else is a neighbour
					 * 
					 * @coauthor Jan Pretzel
					 */
					for (int h = 0; h < HEIGHT; h++) {
						for (int w = 0; w < WIDTH; w++) {

							// first row
							// 00
							if (h - 1 >= 0 && w - 1 >= 0)
								region[0][0] = map[h - 1][w - 1];
							else if (h - 1 < 0 && w - 1 >= 0)
								region[0][0] = map[HEIGHT - 1][w - 1];
							else if (h - 1 >= 0 && w - 1 < 0)
								region[0][0] = map[h - 1][WIDTH - 1];
							else if (h - 1 < 0 && w - 1 < 0)
								region[0][0] = map[HEIGHT - 1][WIDTH - 1];

							// 01
							if (h - 1 >= 0)
								region[0][1] = map[h - 1][w];
							else
								region[0][1] = map[HEIGHT - 1][w];

							// 02
							if (h - 1 >= 0 && w < WIDTH - 1)
								region[0][2] = map[h - 1][w + 1];
							else if (h - 1 < 0 && w < WIDTH - 1)
								region[0][2] = map[HEIGHT - 1][w + 1];
							else if (h - 1 >= 0 && w >= WIDTH - 1)
								region[0][2] = map[h - 1][0];
							else if (h - 1 < 0 && w >= WIDTH - 1)
								region[0][2] = map[HEIGHT - 1][0];

							// middle row
							// 10
							if (w - 1 >= 0)
								region[1][0] = map[h][w - 1];
							else
								region[1][0] = map[h][WIDTH - 1];

							// 11
							region[1][1] = map[h][w];

							// 12
							if (w < WIDTH - 1)
								region[1][2] = map[h][w + 1];
							else
								region[1][2] = map[h][0];

							// last row
							// 20
							if (h < HEIGHT - 1 && w - 1 >= 0)
								region[2][0] = map[h + 1][w - 1];
							else if (h >= HEIGHT - 1 && w - 1 >= 0)
								region[2][0] = map[0][w - 1];
							else if (h < HEIGHT - 1 && w - 1 < 0)
								region[2][0] = map[h + 1][WIDTH - 1];
							else if (h >= HEIGHT - 1 && w - 1 < 0)
								region[2][0] = map[0][WIDTH - 1];

							// 21
							if (h < HEIGHT - 1)
								region[2][1] = map[h + 1][w];
							else
								region[2][1] = map[0][w];

							// 22
							if (h < HEIGHT - 1 && w < WIDTH - 1)
								region[2][2] = map[h + 1][w + 1];
							else if (h >= HEIGHT - 1 && w < WIDTH - 1)
								region[2][2] = map[0][w + 1];
							else if (h < HEIGHT - 1 && w >= WIDTH - 1)
								region[2][2] = map[h + 1][0];
							else if (h >= HEIGHT - 1 && w >= WIDTH - 1)
								region[2][2] = map[0][0];

							// ----------------------------
							nextGen[h][w] = apply(region);
						}
					}
					map = nextGen;
					drawMap(nextGen);
				}
			}
		}).start();
	}
}
