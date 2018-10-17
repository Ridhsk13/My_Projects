CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `player_data` AS
    SELECT 
        `player`.`PName` AS `PName`,
        `player`.`Team` AS `Team`,
        `player`.`Position` AS `Position`,
        `player`.`PNo` AS `PNo`,
        `game`.`GameId` AS `GameId`,
        `game`.`matchDate` AS `matchDate`,
        `game`.`SID` AS `SID`,
        `game`.`teamID` AS `teamID`,
        `game`.`teamID1` AS `teamID1`,
        `game`.`oppositeteam` AS `oppositeteam`
    FROM
        (`player`
        LEFT JOIN `game_lineup` `game` ON (((`player`.`PNo` = `game`.`Pno`)
            AND (`player`.`TeamID` = `game`.`teamID`))))