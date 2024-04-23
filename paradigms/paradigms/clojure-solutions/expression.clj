(require 'clojure.math 'clojure.string)
(defn myDiv
  ([arg] (/ 1.0 arg))
  ([x & args] (/ (double x) (apply * args))))

(defn createOp [op & args] (fn [varMap] (apply op (mapv #(% varMap) args))))

(def add (partial createOp +))
(def subtract (partial createOp -))
(def multiply (partial createOp *))
(def divide (partial createOp myDiv))
(defn negate [arg] (createOp - arg))

(defn sumexpImpl [& args] (apply + (mapv clojure.math/exp args)))
(def sumexp (partial createOp sumexpImpl))

(defn lseImpl [& args] (clojure.math/log (apply sumexpImpl args)))
(def lse (partial createOp lseImpl))

(defn constant [value] (fn [_] (identity value)))
(defn variable [name] (fn [varMap] (varMap name)))

(def tokenToOp {'+ add
                '- subtract
                '* multiply
                '/ divide
                'negate negate
                'sumexp sumexp
                'lse lse
                :constant constant
                :variable variable})
(def variableNames ['x 'y 'z])
(def getCnst :constant)
(def getVar :variable)
(defn in? [coll el] (some #(= el %) coll))

(declare parseExpr)
(defn parseFunction [expr] (parseExpr (read-string expr) tokenToOp))

;;########HW11##########

(load-file "./proto.clj")

(defn opCreator [this op sign diffOp & args]
  (assoc this :op op :sign sign :diffOp diffOp :args args))

(def getOp (field :op))
(def getSign (field :sign))
(def getDiffOp (field :diffOp))
(def getArgs (field :args))
(def getValue (field :value))
(def getName (field :name))
(def toString (method :toString))
(def toStringInfix (method :toStringInfix))
(def evaluate (method :evaluate))
(def diff (method :diff))
(defn removeEl [coll index]
  (concat (take index coll) (drop (inc index) coll)))

(declare Constant Add Multiply ONE MINUS_ONE ZERO)

(def baseOp {:toString (fn [this]
                         (str "(" (getSign this) " "
                              (clojure.string/join " " (mapv #(toString %) (getArgs this))) ")")) 
             :toStringInfix (fn [this] (cond (= (count (getArgs this)) 1) 
                                             (str (getSign this) " " (toStringInfix (first (getArgs this)))) 
                                             :else (str "(" (clojure.string/join (str " " (getSign this) " ") 
                                                             (mapv #(toStringInfix %) (getArgs this))) 
                                                        ")")))
             :evaluate (fn [this varMap]
                         (apply (getOp this) (mapv #(evaluate % varMap) (getArgs this))))
             :diff (fn [this diffName] (apply Add (keep-indexed
                                                   #(Multiply (diff %2 diffName)
                                                              ((getDiffOp this) %1 (getArgs this)))
                                                   (getArgs this))))})

(def baseConst {:toString (fn [this] (str (getValue this)))
                :toStringInfix (fn [this] (str (getValue this)))
                :evaluate (fn [this _] (getValue this))
                :diff (fn [_ __] ZERO)})

(def baseVar {:toString (fn [this] (getName this))
              :toStringInfix (fn [this] (getName this))
              :evaluate (fn [this varMap] (varMap (clojure.string/lower-case (subs (getName this) 0 1))))
              :diff (fn [this diffName] (cond
                                          (= (getName this) diffName) ONE
                                          :else ZERO))})

(def opConstructor (constructor opCreator baseOp))
(defn constCreator [this val]
  (assoc this :value val))
(def constConstructor (constructor constCreator baseConst))

(defn varCreator [this name]
  (assoc this :name name))
(def varConstructor (constructor varCreator baseVar))

(def Add (partial opConstructor + "+" (fn [_ __] ONE)))
(def Subtract (partial opConstructor - "-" (fn [index args] (cond
                                                              (= (count args) 1) MINUS_ONE
                                                              (= index 0) ONE
                                                              :else MINUS_ONE))))

(def Multiply (partial opConstructor * "*" (fn [index args] (apply Multiply (removeEl args index)))))
(def Divide (partial opConstructor myDiv "/" (fn [index args] (cond
                                                                (= (count args) 1) (Divide MINUS_ONE (Multiply (first args) (first args)))
                                                                (= index 0) (Divide ONE (apply Multiply (removeEl args index)))
                                                                :else (Divide
                                                                       (Multiply MINUS_ONE (first args))
                                                                       (apply Multiply (nth args index) (removeEl args 0)))))))

(def Negate (partial opConstructor - "negate" (fn [_ __] MINUS_ONE)))

(defn meansqImpl [& args] (apply + (mapv #(myDiv (* % %) (count args)) args)))
(def Meansq (partial opConstructor meansqImpl "meansq" (fn [index args] (Multiply
                                                                         (Divide
                                                                          (Constant 2)
                                                                          (Constant (count args)))
                                                                         (nth args index)))))
(defn RMSImpl [& args] (clojure.math/sqrt (apply meansqImpl args)))
(def RMS (partial opConstructor RMSImpl "rms" (fn [index args] (Divide (nth args index) (Multiply (apply RMS args) (Constant (count args)))))))

(defn argsToBinary [args] (mapv #(cond (> % 0) 1 :else 0) args))

;; (defn andImpl [& args] (apply bit-and (argsToBinary args)))
;; (defn orImpl [& args] (apply bit-or (argsToBinary args)))
;; (defn xorImpl [& args] (apply bit-or (argsToBinary args)))
(defn notImpl [arg] (cond (>= 0 arg) 1 :else 0))
(defn toInt [arg] (cond (> arg 0) 1 :else 0))
(defn binOpImpl [op & args] (toInt (apply op (argsToBinary args))))
(def And (partial opConstructor (partial binOpImpl bit-and) "&&" nil))
(def Or (partial opConstructor(partial binOpImpl bit-or) "||" nil))
(def Xor (partial opConstructor (partial binOpImpl bit-xor) "^^" nil))
(def Not (partial opConstructor (partial binOpImpl notImpl) "!" nil))

(def Constant (partial constConstructor))
(def ONE (Constant 1))
(def MINUS_ONE (Constant -1))
(def ZERO (Constant 0))

(def Variable (partial varConstructor))

(def tokenToObject {'+ Add
                    '- Subtract
                    '* Multiply
                    '/ Divide
                    'negate Negate
                    'meansq Meansq
                    'rms RMS
                    '&& And
                    '|| Or
                    (symbol "^^") Xor
                    '! Not
                    :constant Constant
                    :variable Variable})

(defn parseExpr [expr opMap]
  (cond
    (number? expr) ((getCnst opMap) expr)
    (in? variableNames expr)  ((getVar opMap) (str expr))
    (contains? opMap (first expr)) (apply (get opMap (first expr)) (mapv #(parseExpr % opMap) (rest expr)))))
(defn parseObject [expr] (parseExpr (read-string expr) tokenToObject))

;;#########HW12#########

(load-file "parser.clj")

(def *all-chars (mapv char (range 0 128)))
(defn *filter [f] (+char (apply str (filter f *all-chars))))
(def *vars (+str (+plus (+char "xyzXYZ"))))
(def *digits (*filter #(Character/isDigit %)))
(def *number (+map read-string (+str (+seqf #(flatten %&) (+opt (+char "-")) (+plus *digits) (+opt (+char ".")) (+opt (+plus *digits))))))
(def *ws (+ignore (+star (*filter #(Character/isWhitespace %)))))
(defn *skipWs [p] (+seqn 0 *ws p *ws))

(defn *operation [sign]
  (+seqf (constantly (tokenToObject sign))
         (apply +seq (mapv (comp +char str) (str sign)))))

(defn *parseOp [parsingFun signs]
  (+seqf (partial apply concat) (*skipWs (+seq parsingFun)) (+seq (+star (+seq (apply +or (mapv *operation signs)) parsingFun))) *ws))

(def *constant (*skipWs (+map Constant *number)))
(def *variable (*skipWs (+map Variable *vars)))
(declare *factor *xor)

(defn objMaker [args]
  (reduce (fn [l [op r]] (op l r)) args))

(defn *binaryOp [parsingFun & signs]
  (+map objMaker (*parseOp parsingFun signs)))

(defn *unaryOp [sign]
  (+map (fn [[op r]] (op r)) (+seq (*operation sign) *ws (delay *factor))))

(def *factor (*skipWs (+or *constant *variable (*unaryOp 'negate) (*unaryOp '!) (+seqn 1 (+char "(") (delay *xor) (+char ")")))))
(def *divMul (*binaryOp *factor '/ '*))
(def *subAdd (*binaryOp *divMul '+ '-))
(def *and (*binaryOp *subAdd '&&))
(def *or (*binaryOp *and '||))
(def *xor (*binaryOp *or (symbol "^^")))

(def parseObjectInfix (+parser *xor))
