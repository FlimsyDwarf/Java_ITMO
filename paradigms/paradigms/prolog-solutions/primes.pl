init(MAX_N) :- sieveEratosthenes(2, MAX_N).

sieveEratosthenes(L, N) :- not composite(L), R is L * 2, sieveEratosthenes(L, R, N).
sieveEratosthenes(L, N) :- L * L =< N, R is L + 1, sieveEratosthenes(R, N).
sieveEratosthenes(C, L, N) :- L < N, assert(composite(L)), R is C + L, sieveEratosthenes(C, R, N).

prime(N) :- not composite(N).

findDivisors(1, _, []) :- !.

findDivisors(Cur, D, Divisors) :- 
    Cur mod D =:= 0,
    NewVal is Cur // D,
    !,
    findDivisors(NewVal, D, NewDiv),
    Divisors = [D | NewDiv].
findDivisors(Cur, D, Divisors) :-
    Cur >= D * D,
    !,
    D1 is D + 1,
    findDivisors(Cur, D1, Divisors).
    
findDivisors(Cur, _, [Cur]).

prime_divisors(1, []) :- !.
prime_divisors(N, Divisors) :- 
    number(N),
    !,
    findDivisors(N, 2, Divisors).

prime_divisors(N, Divisors) :- isRising(Divisors), mult(Divisors, N).

mult([H], H).
mult([H | T], R) :- mult(T, R1), R is H * R1.

less((A, _), (B, _)) :- !, A =< B.
less(A, B) :- A =< B.

isRising([_]) :- !.
isRising([H, M | T]) :- less(H, M), isRising([M | T]), !.

findStep(Num, NewVal, D, Power) :-
    Num mod D =:= 0,
    !,
    NewNum is Num // D,
    findStep(NewNum, NewVal, D , NewPower),
    Power is NewPower + 1.

findStep(Num, Num, _, 0).

fixArray(_, 0, [], []) :- !.
fixArray(Prev, Step, [H | T], Result) :-
    Prev is H,
    !,
    fixArray(Prev, NewStep, T, Result),
    Step is NewStep + 1.
fixArray(_, 0, [H | T], Result) :-
    fixArray(H, NewStep, T, NewResult),
    Step is NewStep + 1,
    Result = [(H, Step) | NewResult].

compact_prime_divisors(1, []) :- !.
compact_prime_divisors(N, CDs) :- 
    number(N), 
    !, prime_divisors(N, Result), fixArray(0, _, Result, CDs).

compact_prime_divisors(N, CDs) :- isRising(CDs), multCds(CDs, N).

fast_power(_, 0, 1).
fast_power(A, B, R) :-
   B > 0, 1 is mod(B, 2),
   B1 is B - 1, fast_power(A, B1, R1),
   R is A * R1.
fast_power(A, B, R) :-
    B > 0, 0 is mod(B, 2),
    B2 is div(B, 2), fast_power(A, B2, R2),
    R is R2 * R2.

multCds([(F, S)], N) :- fast_power(F, S, R), N is R, !.
multCds([(F, S) | T], N) :- multCds(T, N1), fast_power(F, S, R), N is N1 * R.
