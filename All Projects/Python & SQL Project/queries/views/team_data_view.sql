CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `temp` AS
    SELECT 
        `g`.`GameId` AS `GameID`,
        `g`.`matchDate` AS `matchDate`,
        `s`.`SName` AS `SName`,
        `s`.`SCity` AS `SCity`,
        `t1`.`team` AS `team1`,
        `g`.`team1Score` AS `team1Score`,
        `t2`.`team` AS `team2`,
        `g`.`team2Score` AS `team2Score`
    FROM
        (((`game` `g`
        JOIN `stadium` `s`)
        JOIN `team` `t1`)
        JOIN `team` `t2`)
    WHERE
        ((`g`.`SID` = `s`.`SID`)
            AND (`t2`.`teamID` = `g`.`teamID2`)
            AND (`t1`.`teamID` = `g`.`teamID1`))