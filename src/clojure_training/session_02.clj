;;==========================================================================================
;;
;; Sequential Collections
;; 
;;==========================================================================================


;;------------------------------------------------------------------------------------------
;; Vectors
;;------------------------------------------------------------------------------------------

;; Vectors are an indexed, sequential data structure.

[1 2 3]

;; Indexed access

(get ["abc" false 99] 0)

(get ["abc" false 99] 1)

(get ["abc" false 99] 14)

;; count
(count [1 2 3])

;; Constructing using vector
(vector 1 2 3)

;; From a list
(apply vector '(1 2 3)) ;; -> (vector 1 2 3) -> [1 2 3]

;; Adding elements
(conj [1 2 3] 4 5 6)

;; Immutability
(let [v [1 2 3]]
  (conj v 4 5 6)
  v)


;;------------------------------------------------------------------------------------------
;; Lists
;;------------------------------------------------------------------------------------------

;; Lists are sequential linked lists that add new elements at the head of the list,
;; instead of at the tail like vectors.

'(1 2 3)

(conj '(1 2 3) 4 5 6)


;;------------------------------------------------------------------------------------------
;; Stack Access
;;------------------------------------------------------------------------------------------

(def stack '(:a :b))

(peek stack)

(pop stack)




;;==========================================================================================
;;
;; Hashed Collections
;; 
;;==========================================================================================


;;------------------------------------------------------------------------------------------
;; Sets
;;------------------------------------------------------------------------------------------

;; Sets are like mathematical sets - unordered and with no duplicates.
;; Sets are ideal for efficiently checking whether a collection contains
;; an element, or to remove any arbitrary element.

(def players #{"Alice", "Bob", "Kelly"})

(conj players "Fred")

;; Removing from a set

(disj players "Bob" "Sal")

;; checking containment
(contains? players "Kelly")

;; Sorted sets

;; Sorted sets are sorted according to a comparator function which can compare two
;; elements. By default, Clojure???s??compare??function is used, which sorts in "natural"
;; order for numbers, strings, etc.

(conj (sorted-set) "Bravo" "Charlie" "Sigma" "Alpha")

;; A custom comparator can also be used with??sorted-set-by


;; into is used for putting one collection into another
(def players #{"Alice" "Bob" "Kelly"})
(def new-players ["Tim" "Sue" "Greg"])
(into players new-players)


;;------------------------------------------------------------------------------------------
;; Maps
;;------------------------------------------------------------------------------------------

;; Maps are commonly used for two purposes - to manage an association of keys to values
;; and to represent domain application data. The first use case is often referred to as
;; dictionaries or hash maps in other languages.

;; Creating a literal map
(def scores {"Fred"  1400
             "Bob"   1240
             "Angela" 1024})

;; Commas are treated as whitespace in Clojure - feel free to use for improving readability
(def scores {"Fred" 1400, "Bob" 1240, "Angela" 1024})

;; Adding new key-value pairs
(assoc scores "Sally" 0)

;; If the key used in??assoc??already exists, the value is replaced
(assoc scores "Bob" 0)


;; Removing key-value pairs
(dissoc scores "Bob")


;; Looking up by key
(get scores "Angela")


(def directions {:north 0
                 :east 1
                 :south 2
                 :west 3})

(get directions :south)
(directions :south)
(:south directions)


(def bad-lookup-map nil)
(bad-lookup-map :foo) ;; exception
(:foo bad-lookup-map) ;; nil
(get bad-lookup-map :foo) ;; nil



;; Looking up with a default
(get scores "Sam" 0)
(directions :northwest -1)
(:northwest directions -1)

;; Checking contains
(contains? scores "Fred")

(find scores "Fred")

;; Keys or values
(keys scores)
(vals scores)

;; Building a map

;; The??zipmap??function can be used to "zip" together two sequences (the keys and vals)
;; into a map

(zipmap players (repeat 0))


;; Combining maps

(def new-scores {"Angela" 300 "Jeff" 900})
(merge scores new-scores)

;; If both maps contain the same key, the rightmost one wins.
;; Alternately, you can use??merge-with??to supply a function to invoke when there is a conflict

(def new-scores {"Fred" 550 "Angela" 900 "Sam" 1000})
(merge-with + scores new-scores)



;; Nested entities

(def company
  {:name "WidgetCo"
   :address {:street "123 Main St"
             :city "Springfield"
             :state "IL"}})

(get-in company [:address :city])

(assoc-in company [:address :street] "303 Broadway")


;;------------------------------------------------------------------------------------------
;; Records
;;------------------------------------------------------------------------------------------

;; An alternative to using maps is to create a "record".
;; Records are designed specifically for this use case and
;; generally have better performance. In addition, they have
;; a named "type" which can be used for polymorphic behavior.


;; Define a record structure
(defrecord Person [first-name last-name age occupation])

;; Positional constructor - generated
(def kelly (->Person "Kelly" "Keen" 32 "Programmer"))

(def kelly (map->Person
            {:first-name "Kelly"
             :last-name "Keen"
             :age 32
             :occupation "Programmer"}))

(:occupation kelly)
(kelly :occupation) ;; records cannot be invoked as a function like maps



;;==========================================================================================
;;
;; Flow Control Expressions
;; 
;;==========================================================================================

;; Remember that Clojure doesn't have statements, only expressions, I.e. everything returns
;; a value. Expressions that produce onyl side-effects return nil.


;;------------------------------------------------------------------------------------------
;; if
;;------------------------------------------------------------------------------------------

(if (even? 2) "even" "odd")
(if (true? false) "impossible!") ;; else is optional


;;------------------------------------------------------------------------------------------
;; Truth
;;------------------------------------------------------------------------------------------

;; The only false values in Clojure are 'false' and 'nil'

(if true :truthy :falsey)

(if (Object.) :truthy :falsey)

(if [] :truthy :falsey)

(if 0 :truthy :falsey)

(if false :truthy :falsey)

(if nil :truthy :falsey)




;;------------------------------------------------------------------------------------------
;; if and do
;;------------------------------------------------------------------------------------------


(if (even? 5)
  (do (println "even")
      true)
  (do (println "odd")
      false))




;;------------------------------------------------------------------------------------------
;; when
;;------------------------------------------------------------------------------------------

(when (even? 6)
  (println "even")
  true)




;;------------------------------------------------------------------------------------------
;; cond
;;------------------------------------------------------------------------------------------

(let [x 5]
  (cond
    (< x 2) "x is less than 2"
    (< x 10) "x is less than 10"))




;;------------------------------------------------------------------------------------------
;; cond and else
;;------------------------------------------------------------------------------------------

(let [x 11]
  (cond
    (< x 2)  "x is less than 2"
    (< x 10) "x is less than 10"
    :else  "x is greater than or equal to 10"))




;;------------------------------------------------------------------------------------------
;; case
;;------------------------------------------------------------------------------------------

(defn foo [x]
  (case x
    5 "x is 5"
    10 "x is 10"))

(foo 10)
(foo 11) ;; throws IllegalArgumentException!




;;------------------------------------------------------------------------------------------
;; case with else-expression
;;------------------------------------------------------------------------------------------

(defn foo [x]
  (case x
    5 "x is 5"
    10 "x is 10"
    "x isn't 5 or 10"))

(foo 11)





;;==========================================================================================
;;
;; Iteration for Side Effects
;; 
;;==========================================================================================


;;------------------------------------------------------------------------------------------
;; dotimes
;;------------------------------------------------------------------------------------------

;; Evaluates expression n times and returns nil

(dotimes [i 3]
  (println i))




;;------------------------------------------------------------------------------------------
;; doseq
;;------------------------------------------------------------------------------------------

;; Iterates over a sequence. If the sequence is lazy, it forces evaluation. Returns nil.
(doseq [n (range 3)]
  (println n))




;;------------------------------------------------------------------------------------------
;; doseq with multiple bindings
;;------------------------------------------------------------------------------------------

;; Processes all permutations of sequence content and returns nil

(doseq [letter [:a :b]
        number (range 3)]
  (prn [letter number]))




;;==========================================================================================
;;
;; Clojure's for
;; 
;;==========================================================================================

;; - List comprehension - not a for-loop
;; - Generator function for sequence permutation
;; - Bindings behave like doseq

(for [letter [:a :b]
      number (range 3)]
  [letter number])



;;==========================================================================================
;;
;; Recursion
;; 
;;==========================================================================================


;;------------------------------------------------------------------------------------------
;; loop and recur
;;------------------------------------------------------------------------------------------

;; Functional looping construct
;; - loop defines bindings
;; - recur re-executes loop with new bindings

(loop [i 0]
  (if (< i 10)
    (recur (inc i))
    i))




;;------------------------------------------------------------------------------------------
;; defn and recur
;;------------------------------------------------------------------------------------------

(defn increase [i]
  (if (< i 10)
    (recur (inc i))
    i))

(increase 0)




;;------------------------------------------------------------------------------------------
;; recur for recursion
;;------------------------------------------------------------------------------------------

;; recur must be in 'tail position' (the last expression in the branch) and must provide
;; values for all bound symbols by position. Recursion via recur does not consume stack.

;; Example of a bad way to count rabbit ears recursively (compiler doesn't allow 'recur'
;; here because the recursive function call is not in tail position. A stack overflow
;; will occur if we insert too many rabbits.

(defn bad-rabbit-ears [n-rabbits]
  (if (= n-rabbits 0)
    0
    (+ 2 (bad-rabbit-ears (dec n-rabbits)))))

(bad-rabbit-ears 1111)




;; A correct way to count rabbit ears recursively uses and accumulator, which holds the
;; total number of rabbit ears counted that far and passes it to nexr recursive iteration.
;; Now the 'recur' is allowed, because it can be placed in tail position and the stack
;; frame doesn't need to grow on each iteration.

(defn better-rabbit-ears
  ([n-rabbits]
   (better-rabbit-ears n-rabbits 0))
  ([n-rabbits acc]
   (if (= n-rabbits 0)
     acc
     (recur (dec n-rabbits) (+ 2 acc)))))

(better-rabbit-ears 11111111)




;;==========================================================================================
;;
;; Exceptions
;; 
;;==========================================================================================


;;------------------------------------------------------------------------------------------
;; exception handling
;;------------------------------------------------------------------------------------------

;; try/catch/finally as in Java

(try
  (/ 2 1)
  (catch ArithmeticException e
    "divide by zero")
  (finally
    (println "cleanup")))


;;------------------------------------------------------------------------------------------
;; throwing exceptions
;;------------------------------------------------------------------------------------------

(try
  (throw (Exception. "something went wrong"))
  (catch Exception e (.getMessage e)))


;;------------------------------------------------------------------------------------------
;; exceptions with clojure data
;;------------------------------------------------------------------------------------------

;; ex-info takes a message and a map
;; ex-data gets the map back out (or nil if not created with ex-info)

(try
  (throw (ex-info "There was a problem" {:detail 42}))
  (catch Exception e
    (:detail (ex-data e))))


;;------------------------------------------------------------------------------------------
;; with-open
;;------------------------------------------------------------------------------------------

(let [f (clojure.java.io/writer "/tmp/new")]
  (try
    (.write f "some text")
    (finally
      (.close f))))

;; Can be written:
(with-open [f (clojure.java.io/writer "/tmp/new")]
  (.write f "some text"))




;;==========================================================================================
;;
;; Namespaces
;; 
;;==========================================================================================


;;------------------------------------------------------------------------------------------
;; declaring namespaces
;;------------------------------------------------------------------------------------------

(ns com.some-example.my-app                   ; com/some_example/my_app.clj
  "My app example"
  (:require
   [clojure.set :as set]
   [clojure.set :refer [union intersection]]
   [clojure.string :as string]))


;;------------------------------------------------------------------------------------------
;; java classes and imports
;;------------------------------------------------------------------------------------------

(ns com.some-example.my-app2
  (:import
   [java.util Date UUID]
   [java.io File]))




(ns clojure-training.training
  (:require
   [clojure-training.core :refer :all]))

(add 2 3)



