'use strict'

const commonOperation = operation => (...args) => (...vars) => operation(...args.map(arg => arg(...vars)));
const cnst = value => () => value;
const add = commonOperation((first, second) => first + second);
const subtract = commonOperation((first, second) => first - second);
const multiply = commonOperation((first, second) => first * second);
const divide = commonOperation((first, second) => first / second);
const negate = commonOperation((first) => -first);
const floor = commonOperation((first) => Math.floor(first) );
const ceil = commonOperation((first) => Math.ceil(first));
const madd = commonOperation((first, second, third) => first * second + third);
const one = cnst(1);
const two = cnst(2);

const variable = function (name) {
    return function (x, y, z) {
        switch (name) {
            case 'x': return x;
            case 'y': return y;
            case 'z': return z;
        }
    }
}

const operations = new Map([
    ['+', [add, 2]],
    ['-', [subtract, 2]],
    ['*', [multiply, 2]],
    ['/', [divide, 2]],
    ['negate', [negate, 1]],
    ['^', [ceil, 1]],
    ['_', [floor, 1]],
    ['*+', [madd, 3]],
    ['one', [one, 0]],
    ['two', [two, 0]]
])

const variableNames = new Set(['x', 'y', 'z']);

const parse = function (expression) {
    const st = [];
    const tokens = expression.trim().split(/\s+/);
    tokens.map(function (curToken) {
        if (variableNames.has(curToken)) {
            st.push(variable(curToken));
        } else if (operations.has(curToken)) {
            const curOperation = operations.get(curToken);
            if (curOperation[1] === 0) {
                st.push(curOperation[0]);
            } else {
                st.push(curOperation[0](...st.splice(-curOperation[1])));
            }
        } else {
            st.push(cnst((Number(curToken))));
        }
    })
    return st.pop();
}