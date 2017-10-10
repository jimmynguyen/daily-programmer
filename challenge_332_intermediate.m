% /r/dailyprogrammer challenge #332 - intermediate
%
% https://www.reddit.com/r/dailyprogrammer/comments/71gbqj/20170920_challenge_332_intermediate_training_for/
function challenge_332_intermediate(peaks)
	n = length(peaks);
	path  = -1.*ones(1, n);
	score = ones(1, n);
	for i = n:-1:1
		for j = i+1:n
			if peaks(j) > peaks(i) && score(j) >= score(i)
				path(i) = j;
				score(i) = score(j)+1;
			end
		end
	end
	[~, ndx] = max(score);
	while ndx > 0
		fprintf('%d ', peaks(ndx));
		ndx = path(ndx);
	end
	fprintf('\n');
