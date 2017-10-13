% /r/dailyprogrammer challenge #320 - intermediate
%
% https://www.reddit.com/r/dailyprogrammer/comments/6ilyfi/20170621_challenge_320_intermediate_war_card_game/
function winner = challenge_320_intermediate(player1_deck, player2_deck)
	% get deck size
	num_cards = length(player1_deck) + length(player2_deck);

	while ~isempty(player1_deck) && ~isempty(player2_deck)
		% sanity check to make sure cards aren't disappearing or coming out of thin air
		if num_cards ~= length(player1_deck) + length(player2_deck)
			error('You done goofed son');
		end

		[player1_card, player1_deck] = draw(player1_deck);
		[player2_card, player2_deck] = draw(player2_deck);

		outcome = compare(player1_card, player2_card);

		switch outcome
			case 1 % player 1 wins the battle
				player1_deck = [player1_deck, player1_card, player2_card];
			case 2 % player 2 wins the battle
				player2_deck = [player2_deck, player2_card, player1_card];
			otherwise % tie
				[player1_deck, player2_deck] = go_to_war([player1_card, player2_card], player1_deck, player2_deck);
		end
	end

	winner = get_winner(player1_deck, player2_deck);

function [card, deck] = draw(deck)
	card = deck(1);
	deck = deck(2:end);

function outcome = compare(player1_card, player2_card)
	outcome = 0;
	if player1_card > player2_card
		outcome = 1;
	elseif player1_card < player2_card
		outcome = 2;
	end

function [player1_deck, player2_deck, outcome] = go_to_war(tied_cards, player1_deck, player2_deck)
	% check if we already have a winner
	outcome = get_winner(player1_deck, player2_deck);
	if outcome ~= 3
		return;
	end

	[player1_deck, player2_deck, player1_facedown_cards, player2_facedown_cards] = draw_facedown_cards(player1_deck, player2_deck);

	[player1_card, player1_deck] = draw(player1_deck);
	[player2_card, player2_deck] = draw(player2_deck);

	outcome = compare(player1_card, player2_card);

	% if draw, go to war again
	if outcome == 0
		[player1_deck, player2_deck, outcome] = go_to_war([], player1_deck, player2_deck);
	end

	switch outcome
		case 1 % player 1 wins the war
			player1_deck = [player1_deck, player1_facedown_cards, player1_card, player2_facedown_cards, player2_card, tied_cards];
		case 2 % player 2 wins the war
			player2_deck = [player2_deck, player2_facedown_cards, player2_card, player1_facedown_cards, player1_card, tied_cards];
	end

function [player1_deck, player2_deck, player1_facedown_cards, player2_facedown_cards] = draw_facedown_cards(player1_deck, player2_deck)
	num_facedown_cards = min([length(player1_deck)-1, length(player2_deck)-1, 3]);

	player1_facedown_cards = [];
	player2_facedown_cards = [];

	for i = 1:num_facedown_cards
		[player1_card, player1_deck] = draw(player1_deck);
		[player2_card, player2_deck] = draw(player2_deck);
		player1_facedown_cards(i) = player1_card;
		player2_facedown_cards(i) = player2_card;
	end

function winner = get_winner(player1_deck, player2_deck)
	winner = 0; % tie
	if ~isempty(player1_deck) && isempty(player2_deck)
		winner = 1; % player 1 wins
	elseif isempty(player1_deck) && ~isempty(player2_deck)
		winner = 2; % player 2 wins
	elseif ~isempty(player1_deck) && ~isempty(player2_deck)
		winner = 3; % no winner yet
	end
