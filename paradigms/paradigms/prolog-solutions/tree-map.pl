node(Key, Value, L, R, H, node(Key, Value, L, R, H)).

key(node(Key, _, _, _, _), Key).
value(node(_, Value, _, _, _), Value).
left(node(_, _, L, _, _), L).
right(node(_, _, _, R, _), R).
height(node(_, _, _, _, H), H).

max(A, B, R) :- A =< B, R = B, !.
max(A, _, A).

map_build(ListMap, TreeMap) :- tree_build(ListMap, null, TreeMap).

tree_build([], CurNode, CurNode).
tree_build([(Key, Value) | T], CurNode, Result) :- 
    map_put(CurNode, Key, Value, NewNode),
    tree_build(T, NewNode, Result).

map_put(Tree, Key, Value, Result) :-
    node(Key, Value, null, null, 0, Node),
    insert(Tree, Node, Result).

insert([], Node, Node) :- !.
insert(null, Node, Node) :- !.
insert(node(TrK, TrV, TrL, TrR, TrH), node(K, V, L, R, H), Result) :-
    K < TrK,
    !,
    insert(TrL, node(K, V, L, R, H), NewL),
    balance(node(TrK, TrV, NewL, TrR, TrH), Result).

insert(node(TrK, TrV, TrL, TrR, TrH), node(K, V, L, R, H), Result) :-
    K =:= TrK,
    !, node(K, V, TrL, TrR, TrH, Result).
insert(node(TrK, TrV, TrL, TrR, TrH), node(K, V, L, R, H), Result) :-
    !,
    insert(TrR, node(K, V, L, R, H), NewR),
    recalc(node(TrK, TrV, TrL, NewR, TrH), _, Tmp),
    balance(Tmp, Result).

height_diff(L, null, Result) :-
    height(L, L_Height),
    Result is L_Height.
height_diff(null, R, Result) :-
    height(R, R_Height),
    Result is -R_Height.
height_diff(L, R, Result) :-
    height(R, R_Height), height(L, L_Height),
    Result is L_Height - R_Height.

balance(Tr, Result) :-
    recalc(Tr, _, NewTr), balance_impl(NewTr, Result).

balance_impl(node(TrK, TrV, TrL, TrR, TrH), Result) :-
    height_diff(TrL, TrR, -2), !,
    left(TrR, Left),
    right(TrR, Right),
    height_diff(Left, Right, Delt),
    (Delt > 0 ->
        rotateRight(TrR, New_TrR),
        rotateLeft(node(TrK, TrV, TrL, New_TrR, TrH), Result);
        rotateLeft(node(TrK, TrV, TrL, TrR, TrH), Result)).
balance_impl(node(TrK, TrV, TrL, TrR, TrH), Result) :-
    height_diff(TrL, TrR, 2), !,
    left(TrL, Left),
    right(TrL, Right),
    height_diff(Left, Right, Delt),
    (Delt < 0 -> 
        rotateLeft(TrL, New_TrR), rotateRight(node(TrK, TrV, New_TrR, TrR, TrH), Result);
        rotateRight(node(TrK, TrV, TrL, TrR, TrH), Result)).

balance_impl(Result, Result). 
    
rotateLeft(node(TrK, TrV, L, null, TrH), node(TrK, TrV, L, null, TrH)) :- !.
rotateLeft(node(TrK, TrV, TrL, node(TrR_K, TrR_V, TrR_L, TrR_R, _), TrH), Result) :-
    node(TrK, TrV, TrL, TrR_L, TrH, Tmp),
    recalc(Tmp, _, NewL),
    node(TrR_K, TrR_V, NewL, TrR_R, 0, V),
    recalc(V, _, Result).

rotateRight(node(K, V, null, R, H), node(K, V, null, R, H)) :- !.
rotateRight(node(TrK, TrV, node(TrL_K, TrL_V, TrL_L, TrL_R, _), TrR, TrH), Result) :-
    node(TrK, TrV, TrL_R, TrR, TrH, Tmp),
    recalc(Tmp, _, NewR),
    node(TrL_K, TrL_V, TrL_L, NewR, 0, V),
    recalc(V, _, Result).

recalc(node(TrK, TrV, null, null, _), Height, Result) :-
    Height is 0, !,
    node(TrK, TrV, null, null, 0, Result).
recalc(node(TrK, TrV, TrL, null, _), Height, Result) :-
    !, recalc(TrL, HL, NewL),
    Height is HL + 1,
    node(TrK, TrV, NewL, null, HL, Result).
recalc(node(TrK, TrV, null, TrR, _), Height, Result) :-
    !,
    recalc(TrR, HR, NewR),
    Height is HR + 1,
    node(TrK, TrV, null, NewR, Height, Result).
recalc(node(TrK, TrV, TrL, TrR, _), Height, Result) :-
    !, recalc(TrR, HR, NewR),
    recalc(TrL, HL, NewL),
    max(HL, HR, Tmp),
    Height is Tmp + 1,
    node(TrK, TrV, NewL, NewR, Height, Result).

map_get(null, _, null) :- fail.
map_get(node(Key, Value, _, _, _), Key, Value) :- !.
map_get(node(TrK, _, TrL, _, _), Key, Value) :-
    Key < TrK,
    map_get(TrL, Key, Value).
map_get(node(_, _, _, TrR, _), Key, Value) :-
   	map_get(TrR, Key, Value).

findMx(node(TrK, TrV, L, null, H), L, node(TrK, TrV, null, null, H)) :- !.
findMx(node(TrK, TrV, TrL, TrR, H), Left, Result) :-
    findMx(TrR, NewR, Result),
    Left = node(TrK, TrV, TrL, NewR, H).


map_remove(null, _, null) :- !.
map_remove(node(K, _, null, R, _), Key, Result) :-
    Key =:= K, !, 
    Result = R.
map_remove(node(K, _, L, null, _), Key, Result) :-
    Key =:= K, !, 
    Result = L.
map_remove(node(K, V, L, R, H), Key, Result) :-
    Key =:= K, !,
    findMx(L, NewL, node(NewK, NewV, _, _, _)),
    node(NewK, NewV, NewL, R, H, TmpResult),
    balance(TmpResult, Result).
map_remove(node(K, V, null, null, H), Key, Result) :- 
		Key \= K, !, node(K, V, null, null, H, Result).
map_remove(node(K, V, L, R, H), Key, Result) :-
		Key < K, !,
		map_remove(L, Key, NewL), balance(node(K, V, NewL, R, H), Result).
map_remove(node(K, V, L, R, H), Key, Result) :-
		!,
		map_remove(R, Key, NewR), balance(node(K, V, L, NewR, H), Result).

map_putIfAbsent(Tr, Key, Value, Result) :-
    \+ map_get(Tr, Key, _), !,
    map_put(Tr, Key, Value, Result).
map_putIfAbsent(Tr, _, _, Tr).
