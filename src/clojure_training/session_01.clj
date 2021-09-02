;;==========================================================================================
;;
;; Literals
;; 
;;==========================================================================================

;;------------------------------------------------------------------------------------------
;; Numeric Types
;;------------------------------------------------------------------------------------------

42        ; integer
-1.5      ; floating point
22/7      ; ration




;;------------------------------------------------------------------------------------------
;; Character Types
;;------------------------------------------------------------------------------------------

"hello"         ; string
\e              ; character
#"[0-9]+"       ; regular expression




;;------------------------------------------------------------------------------------------
;; Symbols and Idents
;;------------------------------------------------------------------------------------------

map             ; symbol
+               ; symbol
clojure.core/+  ; namespaced symbol
nil             ; null value
true false      ; booleans
:alpha          ; keyword
:release/alpha  ; keyword with namespace




;;------------------------------------------------------------------------------------------
;; Literal Collections
;;------------------------------------------------------------------------------------------

'(1 2 3)     ; list
[1 2 3]      ; vector
#{1 2 3}     ; set
{:a 1, :b 2} ; map




;;==========================================================================================
;;
;; Evaluation
;; 
;;==========================================================================================




;;------------------------------------------------------------------------------------------
;; Java (traitional) evaluation
;;------------------------------------------------------------------------------------------

;              characters             bytecode
; Source Code ----------->  Compiler ----------> JVM -> Effect


; (compilation unit = file or class)




;;------------------------------------------------------------------------------------------
;; Clojure Evaluation
;;------------------------------------------------------------------------------------------

;              characters           data structures             bytecode
; Source Code ----------->  Reader -----------------> Compiler ----------> JVM -> Effect
;                             |
;                             |
;                       Developer (repl)


; (compilation unit = expression)




;;------------------------------------------------------------------------------------------
;; Structures vs Semantics
;;------------------------------------------------------------------------------------------

(+ 5 4)

; Structure:  list       symbol    numbers
;             |          |         |   |

;             (          +         5   4    )

;             |          |         |   |
; Semantics:  invocation function  arguments

; * 5 & 4 evaluate to themselves
; * + evaluates to a function
; * evaluating the list will invoke the + function with 5 and 4 as arguments




;;------------------------------------------------------------------------------------------
;; Delaying Evaluation with Quoting
;;------------------------------------------------------------------------------------------

'(1 2 3)
(1 2 3)




;;------------------------------------------------------------------------------------------
;; REPL
;;------------------------------------------------------------------------------------------

; Try these in a repl:

(+ 3 4)
(+ 10 *1)   ; *1 is in most repls the output of the last evaluated expression (*2, *3, etc.)

(require '[clojure.repl :refer :all])    ; import functions from clojure.repl namespace

(doc +)
(doc doc)

(doc apropos)
(apropos "+")

(doc find-doc)
(find-doc "trim")

(doc dir)
(dir clojure.repl)

(doc source)
(source dir)

(+ (* 5 4) (/ 10 2))

(prn "evaluates third" (prn "evaluates first") (prn "evaluates second"))

(def four 4)
(def six 6)

(+ four six)





;;==========================================================================================
;;
;; Functions
;; 
;;==========================================================================================

;;    name   params         body
;;    -----  ------  -------------------
(defn greet  [name]  (str "Hello, " name))

(greet "Tommi")




;;------------------------------------------------------------------------------------------
;; Multi-arity Functions
;;------------------------------------------------------------------------------------------

(defn messenger
  ([]     (messenger "Hello world!"))
  ([msg]   msg))

(messenger)
(messenger "foobar")




;;------------------------------------------------------------------------------------------
;; Variadic Functions
;;------------------------------------------------------------------------------------------

(defn hello [greeting & who]
  (prn greeting who))

(hello "Hello" "Tommi" "Pikachu")




;;------------------------------------------------------------------------------------------
;; Anonymous Functions
;;------------------------------------------------------------------------------------------

;;    params         body
;;   ---------  -----------------
(fn  [message]  (println message))


;; Equivalent:

(defn greet [name] (str "Hello, " name))

(def greet (fn [name] (str "Hello, " name)))


;; Anonymous function syntax

;; Equivalent to: (fn [x] (+ 6 x))
#(+ 6 %)

;; Equivalent to: (fn [x y] (+ x y))
#(+ %1 %2)

;; Equivalent to: (fn [x y & zs] (println x y zs))
#(println %1 %2 %&)


;; Example:

(map (fn [x] (* 2 x)) [1 2 3])
; vs
(map #(* 2 %) [1 2 3])




;;------------------------------------------------------------------------------------------
;; Applying Functions
;;------------------------------------------------------------------------------------------

(def f #(str %1 %2 %3 %4))
(def values '(3 2 1 0))

(apply f values)

(apply f '(1 2 3 4))    ;; same as  (f 1 2 3 4)
(apply f 1 '(2 3 4))    ;; same as  (f 1 2 3 4)
(apply f 1 2 '(3 4))    ;; same as  (f 1 2 3 4)
(apply f 1 2 3 '(4))    ;; same as  (f 1 2 3 4)





;;==========================================================================================
;;
;; Locals and Closures
;; 
;;==========================================================================================


;;------------------------------------------------------------------------------------------
;; let
;;------------------------------------------------------------------------------------------

;; let binds symbols to values in a "lexical scope".
;; A lexical scope creates a new context for names, nested inside the surrounding context.
;; Names defined in a let take precedence over the names in the outer context.

;;       bindings      name is available here
;;       ------------  ----------------------
;; (let  [name value]  (code that uses name))

(let [x 1
      y 2]
  (+ x y))


;;------------------------------------------------------------------------------------------
;; Closures
;;------------------------------------------------------------------------------------------

;; The fn special form creates a "closure".
;; It "closes over" the surrounding lexical scope
;; and captures their values beyond the lexical scope.


(defn messenger-builder [greeting]
  (fn [who] (println greeting who))) ; closes over greeting

;; greeting provided here, then goes out of scope
(def hello-er (messenger-builder "Hello"))
(def moikka-er (messenger-builder "Moikka"))

;; greeting value still available because hello-er is a closure
(hello-er "Tommi")
(moikka-er "Tommi")




;;==========================================================================================
;;
;; Java Interop
;; 
;;==========================================================================================


;; Task              | Java                  | Clojure
;; ------------------------------------------------------------------
;; Instantiation     | new Widget("foo")     | (Widget. "foo") 
;; Instance method   | rnd.nextInt()         | (.nextInt rnd)
;; Instance field    | object.field          | (.-field object)
;; Static method     | Math.sqrt(25)         | (Math/sqrt 25)
;; Static field      | Math.PI               | Math/PI


;; Try in REPL:

(def sb (java.lang.StringBuilder.))
(.append sb "Hello")
(.append sb " world!")
(def the-string (.toString sb))
the-string

;;------------------------------------------;;
;;                                          ;;
;; Equivalent Java code                     ;;
;;                                          ;;
;;------------------------------------------;;
;;                                          ;;
;; StringBuilder sb = new StringBuilder();  ;;
;; sb.append("Hello");                      ;;
;; sb.append(" world!");                    ;;
;; String theString = sb.toString();        ;;
;;                                          ;;
;;------------------------------------------;;
