(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(defn get-number-for-suit [suit]
  (get {:spade 1 :club 2 :diamond 3 :heart 4} suit)
  )

(defn compare-suites [suit1 suit2]
  (let [suit1-number (get-number-for-suit suit1)
        suit2-number (get-number-for-suit suit2)]
    (if (> suit1-number suit2-number)
      1
      2)
    )
  )

(defn get-number-for-rank [rank]
  (let [map-rank (get {:jack 10 :queen 11 :king 12 :ace 13} rank)]
    (if (= nil map-rank)
      rank
      map-rank
      )
    )
  )

(defn play-round [player1-card player2-card]
  (let [[suit1 rank1] player1-card
        [suit2 rank2] player2-card]
    (if (= rank1 rank2)
      (compare-suites suit1 suit2)
      (if (> (get-number-for-rank rank1) (get-number-for-rank rank2))
        1
        2)))
  )

(defn add-wining-cards-to-deck [player-1-card player-2-card winner-deck]
  (concat winner-deck (shuffle [player-1-card player-2-card]))
  )

(defn play-game [player1-cards player2-cards]
  (let [[player-1-card & player-1-rest] player1-cards
        [player-2-card & player-2-rest] player2-cards]
    (cond
      (empty? player1-cards) "Player 2 won!"
      (empty? player2-cards) "Player 1 won!"
      (= (play-round player-1-card player-2-card) 1) (recur (add-wining-cards-to-deck player-1-card player-2-card player-1-rest) player-2-rest)
      :else (recur player-1-rest (add-wining-cards-to-deck player-1-card player-2-card player-2-rest))))
  )

(defn start-game []
  (let [shuffled-cards (partition (/ (count cards) 2) (shuffle cards))
        player-1-cards (first shuffled-cards)
        player-2-cards (last shuffled-cards)]
    (play-game player-1-cards player-2-cards)
    )
  )

