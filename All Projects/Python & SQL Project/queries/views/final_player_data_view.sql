CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `player_data_with_goals2` AS
    SELECT 
        `p`.`PName` AS `PName`,
        `p`.`Team` AS `Team`,
        `p`.`Position` AS `Position`,
        `p`.`PNo` AS `PNo`,
        `p`.`GameId` AS `GameId`,
        `p`.`matchDate` AS `matchDate`,
        `p`.`SID` AS `SID`,
        `s`.`SName` AS `SName`,
        `s`.`SCity` AS `SCity`,
        `p`.`teamID` AS `teamID`,
        `p`.`teamID1` AS `teamID1`,
        `p`.`oppositeteam` AS `oppositeteam`,
        `g`.`Gtime` AS `Gtime`,
        `g`.`Penalty` AS `Penalty`
    FROM
        ((`player_data` `p`
        LEFT JOIN `stadium` `s` ON ((`s`.`SID` = `p`.`SID`)))
        LEFT JOIN `goal` `g` ON (((`p`.`PNo` = `g`.`Pno`)
            AND (`p`.`teamID` = `g`.`teamID`)
            AND (`p`.`GameId` = `g`.`GameId`))))