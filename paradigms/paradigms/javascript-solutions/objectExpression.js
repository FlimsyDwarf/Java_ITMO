'use strict'
function BaseOperation(operation, diffOp, sign, ...args) {
    this.args = args;
    this.toString = function () {
        let res = '';
        args.map(arg => res += arg.toString() + ' ');
        return res + sign;
    }
    this.evaluate = function (x, y, z) {
        return operation(...args.map(arg => arg.evaluate(x, y, z)));
    }
    this.diff = function (name) {
        return diffOp(name, ...args);
    }
    this.prefix = function () {
        let res = '(' + sign + ' ';
        for (let i = 0; i < this.args.length; i++) {
            res += this.args[i].prefix() + (i === this.args.length - 1 ? '' : ' ');
        }
        return res + ')';
    }
    this.postfix = function () {
        let res = '('
        for (let i = 0; i < this.args.length; i++) {
            res += this.args[i].postfix() + (i === this.args.length - 1 ? '' : ' ');
        }
        return res + ' ' + sign + ')';
    }
}

function Const(val) {
    this.toString = () => val.toString();
    this.evaluate = () => Number(val);
    this.diff = () => new Const(0);
    this.prefix = () => val.toString();
    this.postfix = () => val.toString();
}

function Variable(name) {
    this.toString = () => name;
    this.prefix = () => name;
    this.postfix = () => name;
    this.evaluate = function(x, y, z) {
        switch(name) {
            case 'x': return x;
            case 'y': return y;
            case 'z': return z;
        }
    }
    this.diff = function(diffName) {
        if (name === diffName) {
            return new Const(1);
        }
        return new Const(0);
    }
}
const variableNames = new Set(['x', 'y', 'z']);

function newOperation(operation, diffOp, sign) {
    return function(...args) { return new BaseOperation(operation, diffOp, sign, ...args) };
}
const Add = newOperation((...args) => args.reduce((res, arg) => res + arg, 0),
    (name, ...args) => new Add(...args.map(arg => arg.diff(name))), '+');
const Subtract = newOperation((first, second) => first - second,
    (name, first, second) => new Subtract(first.diff(name), second.diff(name)), '-');
const Negate = newOperation(first => -first, (name, first) =>
    new Negate(first.diff(name)), 'negate');
const Divide = newOperation((first, second) => first / second,
    (name, first, second) => new Divide(
        new Subtract(
            new Multiply(
                first.diff(name), second
            ),
            new Multiply(
                first,
                second.diff(name)
            )
        ),
        new Multiply(second, second)), '/');
const Multiply = newOperation((first, second) => first * second,
    (name, first, second) => new Add(
        new Multiply(
            first.diff(name), second
        ),
        new Multiply(
            first,
            second.diff(name)
        )) ,'*');

function newSumsq (sign) {
    const op = (...args) => args.reduce((s, arg) => s + arg * arg, 0);
    const diffOp = (name, ...args) => new Add(
        ...args.map(arg => new Multiply(
                new Const(2), new Multiply(
                    arg.diff(name), arg)
            )
        )
    )
    return newOperation(op, diffOp, sign);
}
const Sumsq2 = newSumsq('sumsq2');
const Sumsq3 = newSumsq('sumsq3');
const Sumsq4 = newSumsq('sumsq4');
const Sumsq5 = newSumsq('sumsq5');

function newDistance (sign) {
    const op = (...args) => Math.sqrt(args.reduce((s, arg) => s + arg * arg, 0));
    const diffOp =  (name, ...args) => new Multiply(
        new Add(
            ...args.map(arg => new Multiply(
                    new Const(2), new Multiply(
                        arg.diff(name), arg)
                )
            )
        ),
        new Divide(
            new Const(0.5), newDistance(sign)(...args))
    );
    return newOperation(op, diffOp, sign);
}
const Distance2 = newDistance('distance2');
const Distance3 = newDistance('distance3');
const Distance4 = newDistance('distance4');
const Distance5 = newDistance('distance5');

const Exp = newOperation((arg) => Math.exp(arg),
    (name, arg) => new Multiply(new Exp(arg), arg.diff(name)),
    'exp');
const Sumexp = newOperation((...args) => args.reduce((sum, arg) => sum + Math.exp(arg), 0),
    (name, ...args) => new Add(...args.map(arg => new Multiply(arg.diff(name),
        new Exp(arg)))), 'sumexp');

// :NOTE: копипаста с Sumexp
const LSE = newOperation((...args) => Math.log(args.map(arg => Math.exp(arg)).reduce((sum, arg) => sum + arg, 0)),
    (name, ...args) => new Divide(new Add(...args.map(arg => new Exp(arg).diff(name))), new Sumexp(...args)), 'lse');

const operations = new Map([
    ['+', [Add, 2]],
    ['-', [Subtract, 2]],
    ['*', [Multiply, 2]],
    ['/', [Divide, 2]],
    ['negate', [Negate, 1]],
    ['sumsq2', [Sumsq2, 2]],
    ['sumsq3', [Sumsq3, 3]],
    ['sumsq4', [Sumsq4, 4]],
    ['sumsq5', [Sumsq5, 5]],
    ['distance2', [Distance2 , 2]],
    ['distance3', [Distance3 , 3]],
    ['distance4', [Distance4 , 4]],
    ['distance5', [Distance5 , 5]],
    ['sumexp', [Sumexp, -1]],
    ['lse', [LSE, -1]]
])

class ParsingError extends Error {
    constructor(message) {
        super(message);
        this.name = "ParsingError";
    }
}

let id;
const parseTokenChk = function (tokens, type) {
    if (tokens[id] === '(') {
        id++;
        const start = id;
        let op;
        const st = [];
        if (operations.has(tokens[id])) {
            op = operations.get(tokens[id++]);
        }
        while (id < tokens.length && tokens[id] !== ')' && !operations.has(tokens[id])) {
            st.push(parseTokenChk(tokens, type));
        }
        if (operations.has(tokens[id])) {
            if (op !== undefined) {
                throw new ParsingError("unexpected operation: " + tokens[id] + " at token: " + (id + 1));
            }
            op = operations.get(tokens[id++]);
        }
        if (op === undefined) {
            throw new ParsingError("missing operation at token:" + (id + 1));
        }
        if (tokens[id] !== ')') {
            throw new ParsingError("missing closing bracket at token:" + (id + 1));
        }
        id++;
        if (st.length !== op[1] && op[1] !== -1) {
            throw new ParsingError("invalid amount of arguments for operation" + "at token: " + (id + 1));
        }
        if (type === 'prefix' && !operations.has(tokens[start])) {
            throw new ParsingError("expected operation at token number: " + (start+ 1));
        }
        if (type === 'postfix' && !operations.has(st[id - 1])) {
            throw new ParsingError("expected operation at token number: " + (id + 1));
        }
        return new op[0](...st);
    } else if (variableNames.has(tokens[id])) {
        return new Variable(tokens[id++]);
    } else if (!isNaN(tokens[id])) {
        return new Const(tokens[id++]);
    } else {
        throw new ParsingError("unknown token: \'" + tokens[id] + "\' at token number: " + (id + 1));
    }
}

const parseChk = function (expression, type) {
    // :NOTE: можно 1 сплитом /\s+|(\()|(\))/
    const tokens = expression.trim().replaceAll('(', ' ( ').replaceAll(')', ' ) ').split(/\s+/).filter(arg => arg);
    id = 0;
    if (tokens.length === 0) {
        throw new ParsingError ("empty input");
    }
    const result = parseTokenChk(tokens, 0, type);
    if (tokens[0] !== '(' && !variableNames.has(tokens[0]) && isNaN(tokens[0])) {
        throw new ParsingError ("missing \'(\' ");
    }
    if (id !== tokens.length) {
        throw new ParsingError ("end of expression expected at token number: " + (id + 1));
    }
    return result;
}

const parsePrefix = function (expression) {
    return parseChk(expression, 'prefix');
}

const parsePostfix = function (expression) {
    return parseChk(expression, 'postfix');
}

const parseToken = function (st, curToken) {
    if (variableNames.has(curToken)) {
        st.push(new Variable(curToken));
    } else if (operations.has(curToken)) {
        const curOperation = operations.get(curToken);
        st.push(new curOperation[0](...st.splice(-curOperation[1])));
    } else if (!isNaN(curToken)) {
        st.push(new Const(Number(curToken)));
    }
    return st;
}
const parse = function (expression) {
    const tokens = expression.trim().split(/\s+/);
    return tokens.reduce((st, curToken) => parseToken(st, curToken), []).pop();
}

const exp =  new Const(10);

console.log(exp.evaluate(0,0,0));

// :NOTE: Invalid unary (0 args)   : org.graalvm.polyglot.PolyglotException: Error: invalid amount of arguments for operationat token: 4
// стоит сказать какое количество аргументов правильное и сколько ты нашел