lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

arg_to_bin(A, R) :- (A > 0 -> R is 1; R is 0).

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- (B =:= 0 -> R is 0; R is A / B).
operation(op_negate, A, R) :- R is -A.
operation(op_and, A, B, R) :- 
    arg_to_bin(A, Bit_A), arg_to_bin(B, Bit_B),
    R is Bit_A /\ Bit_B.
operation(op_or, A, B, R) :-
    arg_to_bin(A, Bit_A), arg_to_bin(B, Bit_B),
    R is Bit_A \/ Bit_B.
operation(op_xor, A, B, R) :-
    arg_to_bin(A, Bit_A), arg_to_bin(B, Bit_B),
    operation(op_not, Bit_B, NB),
    operation(op_not, Bit_A, NA),
    operation(op_and, Bit_A, NB, Left),
    operation(op_and, NA, Bit_B, Right),
    operation(op_or, Left, Right, R).
operation(op_not, A, R) :-
    arg_to_bin(A, Bit_A),
    (Bit_A > 0 -> R is 0; R is 1).


evaluate(const(Value), _, Value).
evaluate(variable(Name), Vars, R) :- 
    atom_chars(Name, [H | T]),
    lookup(H, Vars, R).
evaluate(operation(Op, A, B), Vars, R) :- 
    evaluate(A, Vars, AV), 
    evaluate(B, Vars, BV), 
    operation(Op, AV, BV, R).

evaluate(operation(Op, A), Vars, R) :-
	  evaluate(A, Vars, AV),  
    operation(Op, AV, R).

:- load_library('alice.tuprolog.lib.DCGLibrary').

% Грамматика для переменных
expr_p(variable(Var)) -->
    { nonvar(Var, atom_chars(Var, Chars)) },
    valid_name(Chars),
    { atom_chars(Var, Chars) }.

valid_name([]) --> [].
valid_name([H | T]) --> 
    { member(H, [x, y, z, 'X', 'Y', 'Z']) }, [H],
    valid_name(T).

% Грамматика для констант
expr_p(const(Value)) -->
    { nonvar(Value, number_chars(Value, Chars)) },
    digits_p(Chars),
    { Chars \= ['-'], Chars = [_ | _], number_chars(Value, Chars) }.

% Так что перепишем рекурсивно
digits_p([]) --> [].
% :NOTE: лишняя проверка? number_chars уже проверит что число корректное
digits_p([H | T]) -->
    { member(H, ['-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'])},
    [H],
    digits_p(T).

% Грамматика для символов операций
op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].
op_p(op_and) --> ['&', '&'].
op_p(op_or) --> ['|', '|'].
op_p(op_xor) --> ['^', '^'].
op_p(op_not) --> ['!'].

% Грамматика для бинарных операций
expr_p(operation(Op, A, B)) --> ['('], expr_p(A), [' '], op_p(Op), [' '], expr_p(B), [')'].

%Грамматика для унарных операций
expr_p(operation(Op, A)) --> op_p(Op), [' '], expr_p(A).

% :NOTE: это должно быть частью грамматики
skip_ws_impl([' '], []) :- !.
skip_ws_impl([H], R) :- R = [H], !.
skip_ws_impl([' ', ')' | T], R) :-
	!,
	skip_ws_impl([')' | T], NR),
	R = NR.
skip_ws_impl(['(', ' ' | T], R) :-
	!,
	skip_ws_impl(['(' | T], NR),
	R = NR.
skip_ws_impl([' ', ' ' | T], R) :-
	!,
	skip_ws_impl([' ' | T], NR),
	R = NR.
skip_ws_impl([H | T], R) :-
	!, skip_ws_impl(T, NR),
	R = [H | NR].

skip_ws([' ' | T], R) :- skip_ws(T, R), !.
skip_ws(T, R) :- skip_ws_impl(T, R).


% Преобразование в строку
expr_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C).
expr_str(E, A) :- 
    atom(A),
    atom_chars(A, C),
    skip_ws(C, NC),
    phrase(expr_p(E), NC).

infix_str(Expr, R) :-
    expr_str(Expr, R).
    