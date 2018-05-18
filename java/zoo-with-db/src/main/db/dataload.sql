-- m_animal マスタデータのセット
INSERT INTO m_animal(
            cd, name, type)
    VALUES
    (1, 'ウサギ', 'jp.co.alpha.zoo.animal.Rabbit'),
    (2, 'トラ', 'jp.co.alpha.zoo.animal.Tiger'),
    (3, 'シマウマ', 'jp.co.alpha.zoo.animal.Zebra'),
    (4, 'フクロウ', 'jp.co.alpha.zoo.animal.Owl');

-- m_cage マスタデータのセット
INSERT INTO m_cage(
            cd, name, type)
    VALUES 
    (1, '普通の檻', 'jp.co.alpha.zoo.cage.NormalCage'),
    (2, '可愛い檻', 'jp.co.alpha.zoo.cage.CuteCage'),
    (3, '頑丈な檻', 'jp.co.alpha.zoo.cage.HardyCage'),
    (4, '広い檻', 'jp.co.alpha.zoo.cage.LargeCage');

-- m_ribbon マスタデータのセット
INSERT INTO m_ribbon(
            cd, name)
    VALUES
    (1, '草食アイドルリボン'),
    (2, '肉食系女子リボン'),
    (3, '空の王者風リボン'),
    (4, 'ふわふわ代表リボン'),
    (5, 'お触られマスターリボン');

