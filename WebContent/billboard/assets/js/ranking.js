import { getRanking } from "./assets/js/get-ranking.js";
const useState = React.useState;
const useEffect = React.useEffect;

const Ranking = () => {
  const [albums, setAlbums] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    (async () => {
      setIsLoading(true);
      const ranking = await getRanking();
      ranking
        ? setAlbums(ranking.data)
        : setError("ランキングの取得に失敗しました");
      setIsLoading(false);
    })();
  }, []);

  return (
    <>
      <Header />
      <h1>楽曲ランキング</h1>
      {albums &&
        albums.map((album, index) => <Album album={album} rank={index + 1} />)}
      <div>
        {isLoading && <p>Loading...</p>}
        {error && <p>{error}</p>}
        <a href="/index.html">戻る</a>
      </div>
    </>
  );
};

const Album = ({ album, rank }) => {
  return (
    <div className="album">
      <p>rank: {rank}</p>
      <img src={album.album_image_url} alt="album art" />
      <p>アルバム名: {album.album_name}</p>
      <p>曲名: {album.track_name}</p>
      <p>アーティスト名: {album.artist_name}</p>
      <p>アクセス数: {album.access}</p>
    </div>
  );
};

const Header = () => {
  return (
    <header>
      <a href="./index.html" className="logo">
        Billboard Kindai
      </a>
      <nav>
        <ul className="nav-list">
          <li className="nav-list-item">
            <a href="./index.html">ホーム</a>
          </li>
          <li clasName="nav-list-item">
            <a href="./search.html">検索</a>
          </li>
        </ul>
      </nav>
    </header>
  );
};

const container = document.getElementById("root");
const root = ReactDOM.createRoot(container);
root.render(<Ranking />);
