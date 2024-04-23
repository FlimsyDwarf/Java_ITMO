(defn checkSizes [elements]
  (every? (fn [element] (== (count (first elements)) (count element))) elements))
(defn isVector [vec] (and (vector? vec) (every? #(number? %) vec)))
(defn checkVecSizes [args] (let [n (count (first args))] (every? #(== n (count %)) args)))
(defn isMatrix [matrix] (and (vector? matrix) (every? (fn [vec] (isVector vec)) matrix) (checkVecSizes matrix)))
(defn checkMatrixSizes [args] (and (every? #(vector? %) args) (checkSizes args)))
(defn vecOp [op args]
  {:pre [(and (every? isVector args) (checkVecSizes args))]}
  (apply mapv op args))


(defn v+ [& args] (vecOp + args))
(defn v- [& args] (vecOp - args))
(defn v* [& args] (vecOp * args))
(defn vd [& args] (vecOp / args))
(defn v*s [vec & nums]
  {:pre [(and (every? #(number? %) nums) (isVector vec))]}
  (let [product (reduce * nums)]
    (mapv (partial * product) vec)))
(defn scalar [& args]
  {:pre [(every? isVector args)]}
  (reduce + (apply v* args)))
;; :NOTE: можно было сделать аккуратнее, записав nth v x в переменные (и не обращаться несколько раз)
(defn vectImpl [a b] [(- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1)))
                      (- (* (nth a 2) (nth b 0)) (* (nth a 0) (nth b 2)))
                      (- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))])
(defn vect [& args]
  {:pre [(and (every? isVector args) (checkVecSizes args))]}
  (reduce vectImpl args))

(defn matrixOp [op & args]
  {:pre [(and (every? isMatrix args) (checkMatrixSizes args))]}
  (apply mapv op args))

(def m+ (partial matrixOp v+))
(def m-  (partial matrixOp v-))
(def m*  (partial matrixOp v*))
(def md  (partial matrixOp vd))

(defn m*s [matrix & nums]
  {:pre [(and (isMatrix matrix) (every? #(number? %) nums))]}
  (let [product (reduce * nums)]
    (mapv (fn [vec] (v*s vec product)) matrix)))

(defn m*v [matrix vec]
  {:pre [(and (isVector vec) (isMatrix matrix) (== (count vec) (count (first matrix))))]}
  (mapv (partial scalar vec) matrix))

(defn transpose [matrix]
  {:pre [isMatrix matrix]
   }
  (apply mapv vector matrix))

(defn m*m [& args]
  {:pre [(every? isMatrix args)]
   :post [isMatrix args]}
  (reduce (fn [a b]
            {:pre [(== (count b) (count (first a)))]}
            (mapv (partial m*v (transpose b)) a)) args))

(defn isTensor [tensor]
  (or (isVector tensor)
      (and (vector? tensor)
           (checkSizes tensor)
           (every? true? (mapv isTensor tensor)))))


(defn tensorOp [op & args]
  {:pre [(isTensor (vec args))]}
  (if (every? number? args)
    (apply op args)
    (apply mapv (partial tensorOp op) args)))

(def t+ (partial tensorOp +))
(def t- (partial tensorOp -))
(def t* (partial tensorOp *))
(def td (partial tensorOp /))
