```shell
Tests: running
WARNING: A command line option has enabled the Security Manager
WARNING: The Security Manager is deprecated and will be removed in a future release
Testing OneFP
    Testing: cnst(10)
Exception in thread "main" java.lang.AssertionError: Error while testing cnst(10): No error expected in 10: org.graalvm.polyglot.PolyglotException: ReferenceError: constants is not defined
        at jstest.expression.BaseTester.test(Unknown Source)
        at jstest.expression.Builder.lambda$selector$4(Unknown Source)
        at base.Selector$Composite.lambda$v$0(Unknown Source)
        at base.Selector.lambda$test$2(Unknown Source)
        at base.Log.lambda$action$0(Unknown Source)
        at base.Log.silentScope(Unknown Source)
        at base.Log.scope(Unknown Source)
        at base.Log.scope(Unknown Source)
        at base.Selector.lambda$test$3(Unknown Source)
        at java.base/java.lang.Iterable.forEach(Iterable.java:75)
        at base.Selector.test(Unknown Source)
        at base.Selector.main(Unknown Source)
        at jstest.functional.FullFunctionalTest.main(Unknown Source)
Caused by: jstest.EngineException: No error expected in 10: org.graalvm.polyglot.PolyglotException: ReferenceError: constants is not defined
        at jstest.JSEngine.eval(Unknown Source)
        at jstest.JSExpressionEngine.parse(Unknown Source)
        at jstest.JSExpressionEngine.parse(Unknown Source)
        at jstest.expression.BaseTester.parse(Unknown Source)
        ... 13 more
Caused by: javax.script.ScriptException: org.graalvm.polyglot.PolyglotException: ReferenceError: constants is not defined
        at org.graalvm.js.scriptengine/com.oracle.truffle.js.scriptengine.GraalJSScriptEngine.toScriptException(GraalJSScriptEngine.java:503)
        at org.graalvm.js.scriptengine/com.oracle.truffle.js.scriptengine.GraalJSScriptEngine.eval(GraalJSScriptEngine.java:480)
        at org.graalvm.js.scriptengine/com.oracle.truffle.js.scriptengine.GraalJSScriptEngine.eval(GraalJSScriptEngine.java:446)
        at java.scripting/javax.script.AbstractScriptEngine.eval(AbstractScriptEngine.java:262)
        ... 17 more
Caused by: org.graalvm.polyglot.PolyglotException: ReferenceError: constants is not defined
        at <js>.parse(<eval>:45)
        at <js>.:program(<eval>:1)
        at org.graalvm.sdk/org.graalvm.polyglot.Context.eval(Context.java:399)
        at org.graalvm.js.scriptengine/com.oracle.truffle.js.scriptengine.GraalJSScriptEngine.eval(GraalJSScriptEngine.java:478)
        ... 19 more
ERROR: Tests: failed
```