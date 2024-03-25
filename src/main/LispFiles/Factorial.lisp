(defun factorial (n)
  (cond ((= n 0) 1)
        (t (* n (factorial (+ n -1))))))

(print (factorial 5))

(print (* 4 99))

(print (/ 1000 5))

(print (- 7 10))