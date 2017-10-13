% /r/dailyprogrammer challenge #320 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/6i60lr/20170619_challenge_320_easy_spiral_ascension/
function canvas = challenge_320_easy(n, varargin)
	is_clockwise = isempty(varargin) || varargin{1}

	canvas = zeros(n, n);
	[x, y] = deal(1);
	if is_clockwise
		[dx, dy] = deal(1, 0);
	else
		[dx, dy] = deal(0, -1);
	end

	for i = 1:n.^2
		canvas(y, x) = i;

		if any([x + dx > n, y - dy > n, y - dy <= 0, x + dx <= 0]) || canvas(y - dy, x + dx)
			if is_clockwise
				[dx, dy] = deal(dy, -dx);
			else
				[dx, dy] = deal(-dy, dx);
			end
		end

		[x, y] = deal(x + dx, y - dy);
	end

	canvas = num2str(canvas);
