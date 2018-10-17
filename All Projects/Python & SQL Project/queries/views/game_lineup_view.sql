CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `game_lineup` AS
    SELECT 
        `game`.`GameId` AS `GameId`,
        `game`.`matchDate` AS `matchDate`,
        `game`.`SID` AS `SID`,
        `startinglineup`.`teamID` AS `teamID`,
        `game`.`teamID1` AS `teamID1`,
        `game`.`teamID2` AS `oppositeteam`,
        `startinglineup`.`Pno` AS `Pno`
    FROM
        (`game`
        JOIN `startinglineup`)
    WHERE
        (`game`.`GameId` = `startinglineup`.`GameId`)