% /r/dailyprogrammer challenge #326 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/6s70oh/2017087_challenge_326_easy_nearest_prime_numbers/
function output = challenge_326_easy(n)
	tic
	if is_prime(n)
		fprintf('%d is prime\n', n);
	else
		fprintf('%d < %d < %d\n', find_nearest_prime(n, false), n, find_nearest_prime(n, true));
	end
	toc

function n = find_nearest_prime(n, increment)
	if mod(n, 2) == 0
		if increment
			n = n + 1;
		else
			n = n - 1;
		end
		if is_prime(n)
			return;
		end
	end

	if increment
		diff = 2;
	else
		diff = -2;
	end
	while true
		n = n + diff;
		if is_prime(n)
			return;
		end
	end

function prime = is_prime(n)
	prime = true;
	if n <= 1
		prime = false;
	elseif n <= 3
		prime = true;
	elseif mod(n, 2) == 0 || mod(n, 3) == 0
		prime = false;
	end
	i = 5;
	while i.^2 <= n
		if mod(n, i) == 0 || mod(n, i+2) == 0
			prime = false;
			break;
		end
		i = i+6;
	end
