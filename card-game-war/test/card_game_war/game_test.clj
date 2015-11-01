(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= 2 (play-round [:heart 7] [:spade :jack]))))
  (testing "queens are higher rank than jacks"
    (is (= 1 (play-round [:heart :queen] [:spade :jack]))))
  (testing "kings are higher rank than queens"
    (is (= 2 (play-round [:heart :queen] [:spade :king]))))
  (testing "aces are higher rank than kings"
    (is (= 2 (play-round [:heart :king] [:spade :ace]))))
  (testing "if the ranks are equal, clubs beat spades"
    (is (= 1 (play-round [:club 7] [:spade 7]))))
  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= 2 (play-round [:club 7] [:diamond 7]))))
  (testing "if the ranks are equal, hearts beat diamonds"
    (is (= 1 (play-round [:heart 7] [:diamond 7])))))

(deftest test-play-game
  (testing "the player 1 wins when player 2 run out of cards"
    (is (= "Player 1 won!" (play-game [[:diamond 3] [:heart 4]] [[:spade 3] [:club 4]]))))
  (testing "the player 2 wins when player 1 run out of cards"
    (is (= "Player 2 won!" (play-game [[:spade 3] [:club 4]] [[:diamond 3] [:heart 4]])))))

