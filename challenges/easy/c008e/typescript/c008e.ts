/*
 * Solution to /r/dailyprogrammer Challenge #8 Easy
 * Jimmy Nguyen
 * 
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pserp/2162012_challenge_8_easy/
 * 
 * Usage:
 *     1. install npm dependencies
 *         npm install
 *     2. run program
 *         npm start
 */
import { sprintf } from 'sprintf-js';

interface IData {
	numBottles: number;
	numBottlesLabel: string;
	numBottlesRepeatLabel: string;
	numBottlesRemainingLabel: string;
	actionLabel: string;
}

class c008 {
	private static LYRIC_TEMPLATE: string = '%(numBottlesLabel)s of beer on the wall, %(numBottlesRepeatLabel)s of beer.\n%(actionLabel)s, %(numBottlesRemainingLabel)s of beer on the wall.\n';
	private static TAKE_ONE_DOWN_ACTION: string = 'Take one down and pass it around';
	private static GO_TO_STORE_ACTION: string = 'Go to the store and buy some more';

	private static getLabel(numBottles: number, isStartOfSentence: boolean) {
		return (numBottles == 0 ? (isStartOfSentence ? "N" : "n") + "o more" : numBottles.toString()) + " bottle" + (numBottles == 1 ? "" : "s");
	}

	public static print(): void {
		const data: IData = {
			numBottles: 99,
			numBottlesLabel: null,
			numBottlesRepeatLabel: null,
			numBottlesRemainingLabel: null,
			actionLabel: null
		};
		while (data.numBottles >= 0) {
			data.numBottlesLabel = this.getLabel(data.numBottles, true);
			data.numBottlesRepeatLabel = this.getLabel(data.numBottles, false);
			data.actionLabel = data.numBottles == 0 ? this.GO_TO_STORE_ACTION : this.TAKE_ONE_DOWN_ACTION;
			data.numBottlesRemainingLabel = this.getLabel(--data.numBottles < 0 ? 99 : data.numBottles, false);
			console.log(sprintf(this.LYRIC_TEMPLATE, data));
		}
	}
}

c008.print();