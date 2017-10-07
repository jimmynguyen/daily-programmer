% /r/dailyprogrammer challenge #328 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/6v29zk/170821_challenge_328_easy_latin_squares/
function is_latin_square = challenge_328_easy(n, A)
	is_latin_square = true;
	A = reshape(A, n, n);
	for i = 1:n
		is_latin_square = is_latin_square && is_valid(A(i, :)) && is_valid(A(:, i));
	end
	if is_latin_square
		disp(reduce(A));
	end

function valid = is_valid(v)
	valid = all(diff(sort(v)) == ones(1, length(v)-1));

function A = reduce(A)
	[~, c_ndx] = sort(A(1, :));
	[~, r_ndx] = sort(A(:, 1));
	A = A(r_ndx, c_ndx);
